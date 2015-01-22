package main;

public class Person {
  String firstName;
  String secondName;

  public Person(String firstName, String secondName) {
    this.firstName = firstName;
    this.secondName = secondName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getSecondName() {
    return secondName;
  }
}
