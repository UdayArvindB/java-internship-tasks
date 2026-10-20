# Java FAQ Chatbot
A rule-based conversational chatbot built with Java 17. It normalizes text, detects intents using keyword/phrase patterns, and returns curated answers with a fallback prompt.

## Features
- Intent recognition for greetings, help, Java, internship, contact, and exit.
- Text normalization and simple token/phrase matching as a lightweight NLP baseline.
- Conversation loop with clear fallback behavior.
- Easily extendable intent/response catalog; no API key or external dependency required.

## Run
```bash
mvn clean package
java -cp target/classes com.internship.chatbot.Main
```

## Important note
This is a rule-based NLP demonstration, not a trained machine-learning model or a large language model. The intent catalog can be extended, or replaced with an API-backed model later.
