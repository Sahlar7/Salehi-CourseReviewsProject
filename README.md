This project was completed by Sahlar Salehi and two other teammates for CS 3140- Software Development Essentials
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
* Implementation for ```review-list.fxml``` and ```ReviewListController.java```
* Implementation for ```my-reviews.fxml``` and ```MyReviewsController.java```
* Implementation for ```review-editor.fxml``` and ```ReviewEditorController.java```
* Assisted in debugging/refactoring other parts of project
* Assisted in database setup
