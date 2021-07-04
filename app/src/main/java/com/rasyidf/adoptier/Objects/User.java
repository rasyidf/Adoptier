package com.rasyidf.adoptier.Objects;


public class User {

  private String userName;
  private String userPassword;
  private String fullName;
  private String city;
  private String dateOfBirth;
  private String phoneNumber;
  private String profileImageKey;

  public User() {

  }

  public User(String profileImageKey) {
    this.profileImageKey = profileImageKey;

  }

  public User(String userName, String userPassword, String fullName, String city, String dateOfBirth, String phoneNumber, String profileImageKey) {
    this.userName = userName;
    this.userPassword = userPassword;
    this.fullName = fullName;
    this.city = city;
    this.dateOfBirth = dateOfBirth;
    this.phoneNumber = phoneNumber;
    this.profileImageKey = profileImageKey;

  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getProfileImageKey() {
    return profileImageKey;
  }

  public void setProfileImageKey(String profileImageKey) {
    this.profileImageKey = profileImageKey;
  }
}
