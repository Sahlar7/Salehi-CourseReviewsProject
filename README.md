[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/DC1SF4uZ)
# Homework 6 - Responding to Change

## Authors
1) Luke McMeans, cet3qt, [McMeans]
2) Nolan Hill, njq4gy, [nolanreedhill]
3) Sahlar Salehi, rmh7ce, [Sahlar7]

## To Run

As always, make sure to run ```./gradlew build``` in the command line before running for the first time.

Before running, make sure you have the proper configurations for this project. The following how-to will be for the IntelliJ platform:

(NOTE: PLEASE USE JAVA AND JAVAFX VERSION 17.0.9)

* Open ```CourseReviewsApplication.java```
* In the top right of IntelliJ, click the drop-down to the left of the run button and select "Edit configurations..." (see below)

<img width="259" alt="image" src="https://github.com/cs-3140-fa23/hw6-hw6-cet3qt-njq4gy-rmh7ce/assets/121998941/a73198b8-b8e2-49db-88b8-f44e5b45df58">

* Click the plus button and add a new Application
* Name this "CourseReviewsApplication"
* Click "Modify Options" and select "Add VM Options"
* With the new section insert ```--module-path "[PATH TO JAVAFX LIB FOLDER]" --add-modules javafx.controls,javafx.fxml``` where "[PATH TO JAVAFX LIB FOLDER]" is the file path to the lib folder within your JavaFX directory
* Fill the rest of inputs accordingly (see below)

<img width="969" alt="image" src="https://github.com/cs-3140-fa23/hw6-hw6-cet3qt-njq4gy-rmh7ce/assets/121998941/e9b4fd8d-0095-49ff-927b-7bc9d60852a7">

* Run the program as usual

## Contributions

### [Author 1 - Luke McMeans]

* Implementation for ```course-search.fxml``` and ```CourseSearchController.java```
* Implementation for ```add-class.fxml``` and ```AddClassController.java```
* Implementation for ```Catalog.java```
* Refactored and assisted with the debugging of other classes

### [Author 2 - Nolan Hill]

* Handled setup and design of database. 
* Implementation for ```login.fxml``` and ```create-account.fxml```, and the Implementation for their controller classes. 
* Implementation for ```CourseReviewsApplication.java```
* Assisted heavily with debugging and refactoring of other parts of the project. 

### [Author 3 - Sahlar Salehi]
* Implementation for ```review-list.fxml``` and ```ReviewListController.java```
* Implementation for ```my-reviews.fxml``` and ```MyReviewsController.java```
* Implementation for ```review-editor.fxml``` and ```ReviewEditorController.java```
* Assisted in debugging/refactoring other parts of project

* Author 3 contributions
* as a bulleted list
* each line starts with an asterisk and a space

## Issues

List any known issues (bugs, incorrect behavior, etc.) at the time of submission.
