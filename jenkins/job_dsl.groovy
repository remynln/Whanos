folder("Whanos Base Images") {
  description("Whanos Base Images")
}
folder("Projects") {
  description("Projects")
}

languages = ["c", "java", "javascript", "python", "befunge"]

languages.each{ language -> 
  freeStyleJob("Whanos Base Images/whanos-$language") {
    steps {
      shell("echo 'Hello world'")
    }
  }
}

freeStyleJob("Whanos Base Images/Build all base images") {
	publishers {
		downstream(
			languages.collect { language -> "Whanos Base Images/whanos-$language" } // using collect because downstream need a new list
		)
	}
}

freeStyleJob("link-project") {
  parameters{
    stringParam("GIT_URL", null, "Git repository url")
    stringParam("DISPLAY_NAME", null, "Display name")
  }
  steps {
    dsl {
      text('''
              freeStyleJob("Projects/$DISPLAY_NAME") {
                scm {
                  git {
                    remote {
                      name("origin")
                      url("$GIT_URL")
                    }
                  }
                }
                triggers {
                  scm("* * * * *")
                }
                wrappers {
                  preBuildCleanup()
                }
                steps {
                  shell("/jenkins/build.sh \\"$DISPLAY_NAME\\"")
                }
              }
      ''')
    }
  }
}