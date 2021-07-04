package com.rasyidf.adoptier.Objects;


public class Pet {
  private String petType;
  private String petName;
  private String petAge;
  private String petMoreInfo;
  private String uriKey;
  private String petCity;
  private String phoneNumber;
  private String fullName;

  public Pet(String petName, String petAge, String petType, String petMoreInfo, String petCity, String uriKey) {
    this.petName = petName;
    this.petAge = petAge;
    this.petType = petType;
    this.petMoreInfo = petMoreInfo;
    this.uriKey = uriKey;
    this.petCity = petCity;
  }

  public Pet() {

  }

  public Pet(String phoneNumber, String userName) {
    this.phoneNumber = phoneNumber;
    this.fullName = userName;
  }

  public String getName() {
    return petName;
  }

  public void setName(String petName) {
    this.petName = petName;
  }

  public String getAge() {
    return petAge;
  }

  public void setAge(String petAge) {
    this.petAge = petAge;
  }

  public String getType() {
    return petType;
  }

  public void setType(String petType) {
    this.petType = petType;
  }

  public String getPetMoreInfo() {
    return petMoreInfo;
  }

  public void setpetMoreInfo(String petMoreInfo) {
    this.petMoreInfo = petMoreInfo;
  }

  public String getUriKey() {
    return uriKey;
  }

  public void setUriKey(String uriKey) {
    this.uriKey = uriKey;
  }

  public String getPetCity() {
    return petCity;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
}
