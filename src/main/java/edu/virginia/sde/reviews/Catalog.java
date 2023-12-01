package edu.virginia.sde.reviews;

import java.util.*;
public class Catalog {
    private List<Course> courses;

    public Catalog(){
        courses = new ArrayList<>();
    }

    public Catalog(List<Course> courses){
        this.courses = courses;
    }

    public void addCourse(Course course){
        courses.add(course);
    }
    public List<Course> getCourses(){
        return courses;
    }

    public void setCourses(List<Course> courses){
        this.courses = courses;
    }

    public List<Course> getCoursesByMnemonic(String mnemonic){
        List<Course> matches = new ArrayList<>();
        for(Course c : courses){
            if(c.getMnemonic().toUpperCase().equals(mnemonic.toUpperCase())){
                matches.add(c);
            }
        }
        return matches;
    }

    public List<Course> getCoursesByNumber(int number){
        List<Course> matches = new ArrayList<>();
        for(Course c : courses){
            if(c.getCourseNumber() == number){
                matches.add(c);
            }
        }
        return matches;
    }

    public List<Course> getCoursesByTitle(String title){
        List<Course> matches = new ArrayList<>();
        for(Course c : courses){
            if(c.getTitle().toLowerCase().contains(title.toLowerCase())){
                matches.add(c);
            }
        }
        return matches;
    }

    public List<Course> getCoursesInAlphabetMnemonicOrder() {
        return courses.stream().sorted(Comparator.comparing(Course::getMnemonic)).toList();
    }
}
