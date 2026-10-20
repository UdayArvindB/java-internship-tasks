# Student Grade Tracker
A console-based Java 17 application for registering students, recording subject scores, and producing a summary report.

## Features
- Add students and scores with input validation (scores must be 0–100).
- List students, calculate each student's average/grade, and identify class high/low averages.
- Uses small domain/service classes and `ArrayList` collections.
- No external dependencies.

## Run
```bash
mvn clean package
java -cp target/classes com.internship.grades.Main
```
Run from the project directory. Data is held in memory for the current session.

## Design
`Student` owns student data and score calculations; `GradeService` manages the student collection and summary statistics; `Main` handles console input/output.
