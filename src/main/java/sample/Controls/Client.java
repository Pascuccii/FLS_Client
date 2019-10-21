package sample.Controls;

import sample.Connectivity.ConnectionClass;
import sample.enums.Disability;
import sample.enums.MaritalStatus;
import sample.enums.Retiree;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Client {
    private int id;
    private String name;
    private String surname;
    private String patronymic;
    private String birthDate;
    private String passportSeries;
    private String passportNumber;
    private String issuedBy;
    private String issuedDate;
    private String birthPlace;
    private String actualResidenceCity;
    private String actualResidenceAddress;
    private String homeNumber;
    private String mobileNumber;
    private String email;
    private String job;
    private String position;
    private String registrationCity;
    private MaritalStatus maritalStatus;
    private String citizenship;
    private Disability disability;
    private Retiree retiree;
    private String monthlyIncome;
    private String idNumber;

    public Client() {
    }

    public Client(int id, String name, String surname, String patronymic, String birthDate, String passportSeries, String passportNumber, String issuedBy, String issuedDate, String birthPlace, String actualResidenceCity, String actualResidenceAddress, String homeNumber, String mobileNumber, String email, String job, String position, String registrationCity, MaritalStatus maritalStatus, String citizenship, Disability disability, Retiree retiree, String monthlyIncome, String idNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
        this.issuedBy = issuedBy;
        this.issuedDate = issuedDate;
        this.birthPlace = birthPlace;
        this.actualResidenceCity = actualResidenceCity;
        this.actualResidenceAddress = actualResidenceAddress;
        this.homeNumber = homeNumber;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.job = job;
        this.position = position;
        this.registrationCity = registrationCity;
        this.maritalStatus = maritalStatus;
        this.citizenship = citizenship;
        this.disability = disability;
        this.retiree = retiree;
        this.monthlyIncome = monthlyIncome;
        this.idNumber = idNumber;
    }

    //DB
    public void setIdDB(ConnectionClass conn, int id) {
        this.id = id;
    }

    public void setNameDB(ConnectionClass conn, String name) {
        name = name.trim();
        if (name.matches("[а-яА-Я]{2,20}")) {
            this.name = name;
            try {
                String prepStat = "UPDATE clients SET Name = ? WHERE id = ?";
                PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
                preparedStatement.setInt(2, this.id);
                preparedStatement.setString(1, name);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSurnameDB(ConnectionClass conn, String surname) {
        surname = surname.trim();
        if (surname.matches("[а-яА-Я]{2,20}")) {
            this.surname = surname;
            try {
                String prepStat = "UPDATE clients SET Surname = ? WHERE id = ?";
                PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
                preparedStatement.setInt(2, this.id);
                preparedStatement.setString(1, surname);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPatronymicDB(ConnectionClass conn, String patronymic) {
        patronymic = patronymic.trim();
        if (patronymic.matches("[а-яА-Я]{2,30}")) {
            this.patronymic = patronymic;
            try {
                String prepStat = "UPDATE clients SET Patronymic = ? WHERE id = ?";
                PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
                preparedStatement.setInt(2, this.id);
                preparedStatement.setString(1, patronymic);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setBirthDateDB(ConnectionClass conn, String birthDate) {
        birthDate = birthDate.trim();
        if (birthDate.matches("^\\d{4}[-/](((0)[0-9])|((1)[0-2]))[-/]([0-2][0-9]|(3)[0-1])$")) {
            this.birthDate = birthDate;
            try {
                String prepStat = "UPDATE clients SET Birth_date = ? WHERE id = ?";
                PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
                preparedStatement.setInt(2, this.id);
                preparedStatement.setDate(1, Date.valueOf(this.birthDate));
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPassportSeriesDB(ConnectionClass conn, String passportSeries) {
        this.passportSeries = passportSeries;
        try {
            String prepStat = "UPDATE clients SET Passport_series = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
            preparedStatement.setInt(2, this.id);
            preparedStatement.setString(1, this.passportSeries);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPassportNumberDB(ConnectionClass conn, String passportNumber) {
        passportNumber = passportNumber.trim();
        if (passportNumber.matches("^\\d{7}$")) {
            this.passportNumber = passportNumber;
            try {
                String prepStat = "UPDATE clients SET Passport_number = ? WHERE id = ?";
                PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
                preparedStatement.setInt(2, this.id);
                preparedStatement.setString(1, this.passportNumber);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setIssuedByDB(ConnectionClass conn, String issuedBy) {
        this.issuedBy = issuedBy;
        try {
            String prepStat = "UPDATE clients SET Issued_by = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
            preparedStatement.setInt(2, this.id);
            preparedStatement.setString(1, this.issuedBy);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setIssuedDateDB(ConnectionClass conn, String issuedDate) {
        issuedDate = issuedDate.trim();
        if (issuedDate.matches("^\\d{4}[-/](((0)[0-9])|((1)[0-2]))[-/]([0-2][0-9]|(3)[0-1])$")) {
            this.issuedDate = issuedDate;
            try {
                String prepStat = "UPDATE clients SET Issued_date = ? WHERE id = ?";
                PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
                preparedStatement.setInt(2, this.id);
                preparedStatement.setDate(1, Date.valueOf(this.issuedDate));
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setBirthPlaceDB(ConnectionClass conn, String birthPlace) {
        boolean exists = true;
        /*try {
            City.valueOf(birthPlace);
        } catch (IllegalArgumentException e) {
            exists = false;
        }*/
        if(exists) {
        this.birthPlace = birthPlace;
        try {
            String prepStat = "UPDATE clients SET Birth_place = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
            preparedStatement.setInt(2, this.id);
            preparedStatement.setString(1, this.birthPlace);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
    }

    public void setActualResidenceCityDB(ConnectionClass conn, String actualResidenceCity) {
        boolean exists = true;
        /*try {
            City.valueOf(actualResidenceCity);
        } catch (IllegalArgumentException e) {
            exists = false;
        }*/
        if(exists) {
            this.actualResidenceCity = actualResidenceCity;
            try {
                String prepStat = "UPDATE clients SET Actual_residence_city = ? WHERE id = ?";
                PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
                preparedStatement.setInt(2, this.id);
                preparedStatement.setString(1, this.actualResidenceCity);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setActualResidenceAddressDB(ConnectionClass conn, String actualResidenceAddress) {
        this.actualResidenceAddress = actualResidenceAddress;
        try {
            String prepStat = "UPDATE clients SET Actual_residence_adress = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
            preparedStatement.setInt(2, this.id);
            preparedStatement.setString(1, this.actualResidenceAddress);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setHomeNumberDB(ConnectionClass conn, String homeNumber) {
        this.homeNumber = homeNumber;
        try {
            String prepStat = "UPDATE clients SET Home_number = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
            preparedStatement.setInt(2, this.id);
            preparedStatement.setString(1, this.homeNumber);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setMobileNumberDB(ConnectionClass conn, String mobileNumber) {
        this.mobileNumber = mobileNumber;
        try {
            String prepStat = "UPDATE clients SET Mobile_number = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
            preparedStatement.setInt(2, this.id);
            preparedStatement.setString(1, this.mobileNumber);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setEmailDB(ConnectionClass conn, String email) {
        this.email = email;
        try {
            String prepStat = "UPDATE clients SET Email = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
            preparedStatement.setInt(2, this.id);
            preparedStatement.setString(1, this.email);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setJobDB(ConnectionClass conn, String job) {
        this.job = job;
        try {
            String prepStat = "UPDATE clients SET Job = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
            preparedStatement.setInt(2, this.id);
            preparedStatement.setString(1, this.job);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPositionDB(ConnectionClass conn, String position) {
        this.position = position;
        try {
            String prepStat = "UPDATE clients SET Position = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
            preparedStatement.setInt(2, this.id);
            preparedStatement.setString(1, this.position);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setRegistrationCityDB(ConnectionClass conn, String registrationCity) {
        boolean exists = true;
        /*try {
            City.valueOf(registrationCity);
        } catch (IllegalArgumentException e) {
            exists = false;
        }*/
        if(exists) {
            this.registrationCity = registrationCity;
            try {
                String prepStat = "UPDATE clients SET Registration_city = ? WHERE id = ?";
                PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
                preparedStatement.setInt(2, this.id);
                preparedStatement.setString(1, this.registrationCity);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setMaritalStatusDB(ConnectionClass conn, MaritalStatus maritalStatus) {
        this.maritalStatus = (null == maritalStatus) ? MaritalStatus.Unknown : maritalStatus;
        try {
            String prepStat = "UPDATE clients SET Marital_status = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
            preparedStatement.setInt(2, this.id);
            assert maritalStatus != null;
            preparedStatement.setString(1, maritalStatus.toString());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setCitizenshipDB(ConnectionClass conn, String citizenship) {
        boolean exists = true;
        /*try {
            City.valueOf(citizenship);
        } catch (IllegalArgumentException e) {
            exists = false;
        }*/
        if(exists) {
            this.citizenship = citizenship;
            try {
                String prepStat = "UPDATE clients SET Citizenship = ? WHERE id = ?";
                PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
                preparedStatement.setInt(2, this.id);
                preparedStatement.setString(1, this.citizenship);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDisabilityDB(ConnectionClass conn, Disability disability) {
        this.disability = disability;
        try {
            String prepStat = "UPDATE clients SET Disability = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
            preparedStatement.setInt(2, this.id);
            assert disability != null;
            preparedStatement.setString(1, disability.toString());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setRetireeDB(ConnectionClass conn, Retiree retiree) {
        this.retiree = retiree;
        try {
            String prepStat = "UPDATE clients SET Is_retiree = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
            preparedStatement.setInt(2, this.id);
            assert retiree != null;
            preparedStatement.setString(1, retiree.toString());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setMonthlyIncomeDB(ConnectionClass conn, String monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
        try {
            String prepStat = "UPDATE clients SET Monthly_income = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
            preparedStatement.setInt(2, this.id);
            preparedStatement.setDouble(1, Double.parseDouble(this.monthlyIncome));
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setIdNumberDB(ConnectionClass conn, String idNumber) {
        this.idNumber = idNumber;
        try {
            String prepStat = "UPDATE clients SET Id_number = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
            preparedStatement.setInt(2, this.id);
            preparedStatement.setString(1, this.idNumber);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDB(ConnectionClass conn) {
        try {
            String prepStat = "DELETE FROM clients WHERE Id = ?";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(prepStat);
            preparedStatement.setInt(1, this.id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    //Object
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        /*try {
            this.birthDate = birthDate;
        } catch(Exception e) {
            System.out.println("OOOOOOOOOOOOOOOOOOOOO");
            e.printStackTrace();
        }*/
    }

    public String getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getActualResidenceCity() {
        return actualResidenceCity;
    }

    public void setActualResidenceCity(String actualResidenceCity) {
        this.actualResidenceCity = actualResidenceCity;
    }

    public String getActualResidenceAddress() {
        return actualResidenceAddress;
    }

    public void setActualResidenceAddress(String actualResidenceAddress) {
        this.actualResidenceAddress = actualResidenceAddress;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRegistrationCity() {
        return registrationCity;
    }

    public void setRegistrationCity(String registrationCity) {
        this.registrationCity = registrationCity;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public Disability getDisability() {
        return disability;
    }

    public void setDisability(Disability disability) {
        this.disability = disability;
    }

    public Retiree getRetiree() {
        return retiree;
    }

    public void setRetiree(Retiree retiree) {
        this.retiree = retiree;
    }

    public String getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(String monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthDate=" + birthDate +
                ", passportSeries='" + passportSeries + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", issuedBy='" + issuedBy + '\'' +
                ", issuedDate=" + issuedDate +
                ", birthPlace='" + birthPlace + '\'' +
                ", actualResidenceCity=" + actualResidenceCity +
                ", actualResidenceAddress='" + actualResidenceAddress + '\'' +
                ", homeNumber='" + homeNumber + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", email='" + email + '\'' +
                ", job='" + job + '\'' +
                ", position='" + position + '\'' +
                ", registrationCity=" + registrationCity +
                ", maritalStatus=" + maritalStatus +
                ", citizenship=" + citizenship +
                ", disability=" + disability +
                ", retiree=" + retiree +
                ", monthlyIncome=" + monthlyIncome +
                ", idNumber='" + idNumber + '\'' +
                '}';
    }
}
