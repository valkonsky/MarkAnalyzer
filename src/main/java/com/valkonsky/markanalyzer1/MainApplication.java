package com.valkonsky.markanalyzer1;

import com.valkonsky.markanalyzer1.entity.School;
import com.valkonsky.markanalyzer1.entity.SchoolClass;
import com.valkonsky.markanalyzer1.entity.Student;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class MainApplication extends Application {

    private Stage stage;
    private File file;

    BorderPane general;

    private School school;

    @Override
    public void start(Stage stage) throws IOException {

        this.stage = stage;
        stage.setTitle("Paragraph Add-on");
        Button button = new Button("Выберите файл");

        button.setOnAction(event ->{
                file = new FileChooser().showOpenDialog(stage);
            try {
                school = new School(file.getAbsolutePath());
                ListView classes = new ListView(initClasses());
                general = new BorderPane();
                general.setLeft(classes);
                classes.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //System.out.println("clicked on " + classes.getSelectionModel().getSelectedItem());
                        getInfoForStudentClass((SchoolClass) school.getSchoolClassesInMap().get(classes.getSelectionModel().getSelectedItem()));
                    }
                });
                stage.setScene(new Scene(general,700,700));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        StackPane pane = new StackPane();
        pane.getChildren().add(button);
        stage.setScene(new Scene(pane,400,200));



        stage.show();

    }

    public static void main(String[] args) throws IOException {
        launch();
    }

    public ObservableList<String> initClasses(){
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i =0;i<school.getSchoolClasses().size();i++){
            items.add(school.getSchoolClasses().get(i).getSchoolClassName());
        }
        return items;
    }

    public ObservableList<String> initExcelentStudents(SchoolClass schoolClass){
        ObservableList<String> excelentStudent = FXCollections.observableArrayList();
        for (Student student:schoolClass.getExcelentStudents()){
            excelentStudent.add(String.valueOf(student));
        }
        return excelentStudent;
    }

    public ObservableList<String> initStudentsWithSingleFour(SchoolClass schoolClass){
        ObservableList<String> studentsWithSingleFour = FXCollections.observableArrayList();
        for (Student student:schoolClass.getStudentsWithSingleFour(schoolClass.getHonorStudents())){
            studentsWithSingleFour.add(String.valueOf(student));
        }
        return studentsWithSingleFour;
    }

    public ObservableList<String> initStudentsWithSingleThree(SchoolClass schoolClass){
        ObservableList<String> studentsWithSingleThree = FXCollections.observableArrayList();
        for (Student student:schoolClass.getStudentsWithSingleThree()){
            studentsWithSingleThree.add(String.valueOf(student));
        }
        return studentsWithSingleThree;
    }

    public ObservableList<String> initStudentsWithNegativeMarks(SchoolClass schoolClass){
        ObservableList<String> studentsWithNegativeMarks = FXCollections.observableArrayList();
        for (Student student:schoolClass.getStudentsWithNegativeMarks()){
            studentsWithNegativeMarks.add(String.valueOf(student));
        }
        return studentsWithNegativeMarks;
    }



    public void getInfoForStudentClass(SchoolClass schoolClass){
        Label className = new Label(" Класс: " + schoolClass.getSchoolClassName());
        Label countOfStudents = new Label(" Учеников в классе: " + schoolClass.getStudents().size());
        Label countOfSchoolSubjects = new Label(" Учебных дисциплин в классе: " + schoolClass.getSchoolSubjects().size());
        Label unAttestedStudents = new Label(" Учеников без оценок по предмету: " + schoolClass.getUnAttestedStudentsInStudentClass().size());
        Label unAttestedStudentsRespect = new Label(" Учеников НАУ: " + schoolClass.getUnAttestedStudentsInStudentClassForRespectReason().size());
        Label unAttestedStudentsUnRespect = new Label(" Учеников НАН: " + schoolClass.getUnAttestedStudentsInStudentClassForUnRespectReason().size());
        Label excelentStudents = new Label(" Отличников: " + schoolClass.getExcelentStudents().size());
        ListView excelentStudentsList = new ListView(initExcelentStudents(schoolClass));
        excelentStudentsList.setMaxSize(300,150);
        int honAndExcel = schoolClass.getHonorStudents().size()+schoolClass.getExcelentStudents().size();
        Label honorAndExcelentStudents = new Label(" Без \"троек\" : " + honAndExcel);
        Label honorStudents = new Label(" Хорошистов : " + schoolClass.getHonorStudents().size());
        Label studentsWithSingleFour = new Label(" C 1 \"четверкой\" : " + schoolClass.getStudentsWithSingleFour(schoolClass.getHonorStudents()).size());
        ListView studentsWithSingleFourList = new ListView(initStudentsWithSingleFour(schoolClass));
        studentsWithSingleFourList.setMaxSize(300,150);
        Label studentsWithSingleThree = new Label(" C 1 \"тройкой\" : " + schoolClass.getStudentsWithSingleThree().size());
        ListView studentsWithSingleThreeList = new ListView(initStudentsWithSingleThree(schoolClass));
        studentsWithSingleThreeList.setMaxSize(300,150);
        Label studentsWithNegativeMarks = new Label(" C  \"двойкой\" или \"НАН\" ");
        ListView studentsWithNegativeMarksList = new ListView(initStudentsWithNegativeMarks(schoolClass));
        studentsWithNegativeMarksList.setMaxSize(300,150);
        Label academicPerformance = new Label(" Качество знаний: " + schoolClass.getAcademicPerfomance() + " %");

        VBox vBox = new VBox(className,countOfStudents,countOfSchoolSubjects,unAttestedStudents,unAttestedStudentsRespect,unAttestedStudentsUnRespect,
                excelentStudents,excelentStudentsList,honorAndExcelentStudents,honorStudents,studentsWithSingleFour,studentsWithSingleFourList,studentsWithSingleThree,
                studentsWithSingleThreeList,studentsWithNegativeMarks,studentsWithNegativeMarksList,academicPerformance);
        general.setCenter(vBox);
    }
}