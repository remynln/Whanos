#!/bin/bash
LANGUAGES=()
if [[ -f Makefile ]]; then
	LANGUAGE+=("c")
fi
if [[ -f app/pom.xml ]]; then
	LANGUAGE+=("java")
fi
if [[ -f package.json ]]; then
	LANGUAGE+=("javascript")
fi
if [[ -f requirements.txt ]]; then
	LANGUAGE+=("python")
fi
if [[ -f app/main.bf ]]; then
	LANGUAGE+=("befunge")
fi

if [[ ${#LANGUAGE[@]} == 0 ]]; then
    echo "Error: no language found"
    exit 1
fi
if [[ ${#LANGUAGE[@]} != 1 ]]; then
    echo "Error: more than 1 language detected"
    exit 1
fi

echo "Language found : ${LANGUAGE[0]}"