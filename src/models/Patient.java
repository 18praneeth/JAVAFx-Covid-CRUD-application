package models;

public class Patient {

    private final int id;
    private final String name;
    private final String patient_id;
    private final String email_id;
    private final int phone_number;
    private final int age;
    private final int aadhar_number;
    private final String is_vaccinated;
    private final String remarks;

    public Patient(int Id, String Name, String Patient_id, int Age, int Aadhar_number, String Is_vaccinated, String Remarks, String email_id, int phone_number){
        this.id = Id;
        this.name = Name;
        this.patient_id = Patient_id;
        this.age = Age;
        this.aadhar_number = Aadhar_number;
        this.is_vaccinated = Is_vaccinated;
        this.remarks = Remarks;
        this.email_id = email_id;
        this.phone_number = phone_number;
    }

    public int getId(){
        return id;
    }

    public int getAadhar_number() {
        return aadhar_number;
    }

    public String getIs_vaccinated() {
        return is_vaccinated;
    }

    public String getName() {
        return name;
    }

    public String getRemarks() {
        return remarks;
    }

    public int getAge() {
        return age;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public String getEmail_id() {
        return email_id;
    }
}
