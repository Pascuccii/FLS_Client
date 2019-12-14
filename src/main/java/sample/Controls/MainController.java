package sample.Controls;

import com.mysql.cj.util.StringUtils;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import sample.Connectivity.ServerConnection;
import sample.enums.Disability;
import sample.enums.MaritalStatus;
import sample.enums.Retiree;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Iterator;

import static java.lang.Thread.sleep;

@SuppressWarnings("ALL")
public class MainController extends Application {
    private static String serverIP = "127.0.0.1";
    private Instant start;
    private Instant stop;
    private double xOffset = 0;
    private double yOffset = 0;
    private User currentUser;
    private String currentTheme;
    private String currentLanguage;
    //private DatabaseConnection connDB;
    private ServerConnection connServer;
    private int theme = 0;
    private TableColumn maritalStatusColumn;
    private TableColumn disabilityColumn;
    private TableColumn retireeColumn;
    private TableColumn deleteLessonColumn;
    private TableColumn deleteTeacherSubjectColumn;
    private TableColumn deleteGroupColumn;
    private TableColumn deleteStudentColumn;
    private TableColumn deleteTeacherColumn;
    private TableColumn deleteSubjectColumn;
    private ObservableList<User> usersData = FXCollections.observableArrayList();
    private ObservableList<Client> clientsData = FXCollections.observableArrayList();
    private ObservableList<Group> groupsData = FXCollections.observableArrayList();
    private ObservableList<Lesson> lessonsData = FXCollections.observableArrayList();
    private ObservableList<Student> studentsData = FXCollections.observableArrayList();
    private ObservableList<Subject> subjectsData = FXCollections.observableArrayList();
    private ObservableList<Teacher> teachersData = FXCollections.observableArrayList();
    private ObservableList<TeacherSubject> teachersSubjectsData = FXCollections.observableArrayList();

    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, Integer> accessModeColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableView<Group> groupsTable;
    @FXML
    private TableColumn<Group, Integer> groupIdColumn;
    @FXML
    private TableColumn<Group, String> groupLevelColumn;

    @FXML
    private TableView<Lesson> lessonsTable;
    @FXML
    private TableColumn<Lesson, Integer> lessonIdColumn;
    @FXML
    private TableColumn<Lesson, String> lessonGroupIdColumn;
    @FXML
    private TableColumn<Lesson, String> lessonTeacherSubjectIdColumn;
    @FXML
    private TableColumn<Lesson, String> lessonCabinetColumn;
    @FXML
    private TableColumn<Lesson, String> lessonDateColumn;
    @FXML
    private TableColumn<Lesson, String> lessonTimeColumn;

    @FXML
    private TableView<Student> studentsTable;
    @FXML
    private TableColumn<Student, Integer> studentIdColumn;
    @FXML
    private TableColumn<Student, String> studentNameColumn;
    @FXML
    private TableColumn<Student, String> studentSurnameColumn;
    @FXML
    private TableColumn<Student, String> studentPatronymicColumn;
    @FXML
    private TableColumn<Student, String> studentGroupIdColumn;
    @FXML
    private TableColumn<Student, String> studentEmailColumn;
    @FXML
    private TableColumn<Student, String> studentPhoneColumn;

    @FXML
    private TableView<Subject> subjectsTable;
    @FXML
    private TableColumn<Subject, Integer> subjectIdColumn;
    @FXML
    private TableColumn<Subject, String> subjectNameColumn;
    @FXML
    private TableColumn<Subject, String> subjectHoursColumn;

    @FXML
    private TableView<Teacher> teachersTable;
    @FXML
    private TableColumn<Teacher, Integer> teacherIdColumn;
    @FXML
    private TableColumn<Teacher, String> teacherNameColumn;
    @FXML
    private TableColumn<Teacher, String> teacherSurnameColumn;
    @FXML
    private TableColumn<Teacher, String> teacherPatronymicColumn;

    @FXML
    private TableView<TeacherSubject> teachersSubjectsTable;
    @FXML
    private TableColumn<TeacherSubject, Integer> teacherSubjectIdColumn;
    @FXML
    private TableColumn<TeacherSubject, String> teacherSubjectTeacherIdColumn;
    @FXML
    private TableColumn<TeacherSubject, String> teacherSubjectSubjectIdColumn;


    @FXML
    private AnchorPane primaryAnchorPane;
    @FXML
    private AnchorPane title;
    @FXML
    private Button hideButton;
    @FXML
    private Button minimizeButton;
    @FXML
    private Button exitButton;
    @FXML
    private AnchorPane workPane;
    @FXML
    private AnchorPane leftAnchorPane;
    @FXML
    private FlowPane menuAdmin;
    @FXML
    private Label currentUserLabelAdmin;
    @FXML
    private Button menuAdminUsersButton;
    @FXML
    private Button menuAdminLessonsButton;
    @FXML
    private Button menuAdminTeachersSubjectsButton;
    @FXML
    private Button menuAdminGroupsButton;
    @FXML
    private Button menuAdminStudentsButton;
    @FXML
    private Button menuAdminTeachersButton;
    @FXML
    private Button menuAdminSubjectsButton;
    @FXML
    private Button menuAdminSettingsButton;
    @FXML
    private Button menuAdminInfoButton;
    @FXML
    private Button logoutButtonAdmin;
    @FXML
    private FlowPane menuUser;
    @FXML
    private Label currentUserLabelUser;
    @FXML
    private Button menuUserSettingsButton;
    @FXML
    private Button menuUserInfoButton;
    @FXML
    private Button logoutButtonUser;
    @FXML
    private Button addLessonButton;
    @FXML
    private Button addTeacherSubjectButton;
    @FXML
    private Button addGroupButton;
    @FXML
    private Button addStudentButton;
    @FXML
    private Button addTeacherButton;
    @FXML
    private Button addSubjectButton;
    @FXML
    private Button serverConnectButton;
    @FXML
    private AnchorPane rightAnchorPane;
    @FXML
    private AnchorPane menuPaneUsers;
    @FXML
    private AnchorPane menuPaneLessons;
    @FXML
    private ScrollPane lessonsScrollPane;
    @FXML
    private AnchorPane lessonsAnchorPane;
    @FXML
    private AnchorPane menuPaneTeachersSubjects;
    @FXML
    private ScrollPane teachersSubjectsScrollPane;
    @FXML
    private AnchorPane teachersSubjectsAnchorPane;
    @FXML
    private AnchorPane menuPaneGroups;
    @FXML
    private ScrollPane groupsScrollPane;
    @FXML
    private AnchorPane groupsAnchorPane;
    @FXML
    private AnchorPane menuPaneStudents;
    @FXML
    private ScrollPane studentsScrollPane;
    @FXML
    private AnchorPane studentsAnchorPane;
    @FXML
    private AnchorPane menuPaneTeachers;
    @FXML
    private ScrollPane teachersScrollPane;
    @FXML
    private AnchorPane teachersAnchorPane;
    @FXML
    private AnchorPane menuPaneSubjects;
    @FXML
    private ScrollPane subjectsScrollPane;
    @FXML
    private AnchorPane subjectsAnchorPane;

    @FXML
    private AnchorPane createLessonAnchorPane;
    @FXML
    private AnchorPane createTeacherSubjectAnchorPane;
    @FXML
    private AnchorPane createGroupAnchorPane;
    @FXML
    private AnchorPane createStudentAnchorPane;
    @FXML
    private AnchorPane createTeacherAnchorPane;
    @FXML
    private AnchorPane createSubjectAnchorPane;

    @FXML
    private TextField searchField;
    @FXML
    private TextField searchFieldLesson;
    @FXML
    private TextField searchFieldTeacherSubject;
    @FXML
    private TextField searchFieldGroup;
    @FXML
    private TextField searchFieldStudent;
    @FXML
    private TextField searchFieldTeacher;
    @FXML
    private TextField searchFieldSubject;
    @FXML
    private MenuButton criteriaButton;
    @FXML
    private MenuItem criteriaMenuItem_Id;
    @FXML
    private MenuItem criteriaMenuItem_Access;
    @FXML
    private MenuItem criteriaMenuItem_Username;
    @FXML
    private MenuItem criteriaMenuItem_Password;
    @FXML
    private MenuItem criteriaMenuItem_Email;
    @FXML
    private MenuButton criteriaButtonLesson;
    @FXML
    private MenuItem criteriaLessonId;
    @FXML
    private MenuItem criteriaLessonGroupId;
    @FXML
    private MenuItem criteriaLessonTeacherSubjectId;
    @FXML
    private MenuItem criteriaLessonCebinet;
    @FXML
    private MenuItem criteriaLessonDate;
    @FXML
    private MenuItem criteriaLessonTime;
    @FXML
    private MenuButton criteriaButtonTeacherSubject;
    @FXML
    private MenuItem criteriaTeacherSubjectId;
    @FXML
    private MenuItem criteriaTeacherSubjectTeacherId;
    @FXML
    private MenuItem criteriaTeacherSubjectSubjectId;
    @FXML
    private MenuButton criteriaButtonGroup;
    @FXML
    private MenuItem criteriaGroupId;
    @FXML
    private MenuItem criteriaGroupLevel;
    @FXML
    private MenuButton criteriaButtonStudent;
    @FXML
    private MenuItem criteriaStudentId;
    @FXML
    private MenuItem criteriaStudentName;
    @FXML
    private MenuItem criteriaStudentSurname;
    @FXML
    private MenuItem criteriaStudentPatronymic;
    @FXML
    private MenuItem criteriaStudentGroupId;
    @FXML
    private MenuItem criteriaStudentEmail;
    @FXML
    private MenuItem criteriaStudentPhone;
    @FXML
    private MenuButton criteriaButtonTeacher;
    @FXML
    private MenuItem criteriaTeacherId;
    @FXML
    private MenuItem criteriaTeacherName;
    @FXML
    private MenuItem criteriaTeacherSurname;
    @FXML
    private MenuItem criteriaTeacherPatronymic;
    @FXML
    private MenuButton criteriaButtonSubject;
    @FXML
    private MenuItem criteriaSubjectId;
    @FXML
    private MenuItem criteriaSubjectName;
    @FXML
    private MenuItem criteriaSubjectHours;


    @FXML
    private Button searchButton;
    @FXML
    private Button searchButtonLesson;
    @FXML
    private Button searchButtonTeacherSubject;
    @FXML
    private Button searchButtonGroup;
    @FXML
    private Button searchButtonStudent;
    @FXML
    private Button searchButtonTeacher;
    @FXML
    private Button searchButtonSubject;
    @FXML
    private Button resetSearchButton;
    @FXML
    private Button resetButtonLessons;
    @FXML
    private Button resetButtonTeachersSubjects;
    @FXML
    private Button resetButtonGroups;
    @FXML
    private Button resetButtonStudents;
    @FXML
    private Button resetButtonTeachers;
    @FXML
    private Button resetButtonSubjects;
    @FXML
    private AnchorPane createUser_AnchorPane;
    @FXML
    private TextField createUser_AnchorPane_Username;
    @FXML
    private TextField createUser_AnchorPane_Password;
    @FXML
    private TextField createUser_AnchorPane_Email;
    @FXML
    private Button createUserButton;
    @FXML
    private MenuButton createUser_AnchorPane_AccessMode_MenuButton;
    @FXML
    private MenuItem createUser_AccessMenuItem_User;
    @FXML
    private MenuItem createUser_AccessMenuItem_Admin;
    @FXML
    private AnchorPane changeUser_AnchorPane;
    @FXML
    private TextField changeUser_AnchorPane_Id;
    @FXML
    private TextField changeUser_AnchorPane_Username;
    @FXML
    private TextField changeUser_AnchorPane_Password;
    @FXML
    private TextField changeUser_AnchorPane_Email;
    @FXML
    private Button changeUser_AnchorPane_IdSubmitButton;
    @FXML
    private Button changeUserButton;
    @FXML
    private TextField addLessonGroupIdTextField;
    @FXML
    private TextField addLessonTeacherSubjectIdTextField;
    @FXML
    private TextField addLessonCabinetTextField;
    @FXML
    private TextField addLessonDateTextField;
    @FXML
    private TextField addLessonTimeTextField;
    @FXML
    private TextField addTeacherSubjectTeacherIdTextField;
    @FXML
    private TextField addTeacherSubjectSubjectIdTextField;
    @FXML
    private TextField addGroupLevelTextField;
    @FXML
    private TextField addStudentNameTextField;
    @FXML
    private TextField addStudentSurnameTextField;
    @FXML
    private TextField addStudentPatronymicTextField;
    @FXML
    private TextField addStudentGroupIdTextField;
    @FXML
    private TextField addStudentEmailTextField;
    @FXML
    private TextField addStudentPhoneTextField;
    @FXML
    private TextField addTeacherNameTextField;
    @FXML
    private TextField addTeacherSurnameTextField;
    @FXML
    private TextField addTeacherPatronymicTextField;
    @FXML
    private TextField addSubjectNameTextField;
    @FXML
    private TextField addSubjectHoursTextField;


    @FXML
    private MenuButton changeUser_AnchorPane_AccessMode_MenuButton;
    @FXML
    private MenuItem changeUser_AccessMenuItem_User;
    @FXML
    private MenuItem changeUser_AccessMenuItem_Admin;
    @FXML
    private AnchorPane deleteUser_AnchorPane;
    @FXML
    private TextField deleteUserTextField;
    @FXML
    private Button deleteUserButton;
    @FXML
    private Label deleteUserLabel;
    @FXML
    private ScrollPane clientManagementScrollPane;
    @FXML
    private AnchorPane clientManagementAnchorPane;
    //ФОРМА ДОБАВЛЕНИЯ КЛИЕНТА
    @FXML
    private AnchorPane createClient_AnchorPane;
    @FXML
    private AnchorPane createClient_AnchorPane_NameJobResidencePane;
    @FXML
    private AnchorPane createClient_AnchorPane_PassportDataPane;
    @FXML
    private AnchorPane createClient_AnchorPane_ContactsOtherPane;
    @FXML
    private Label addClientNameLabel;
    @FXML
    private Label addClientSurnameLabel;
    @FXML
    private Label addClientPatronymicLabel;
    @FXML
    private Label addClientJobLabel;
    @FXML
    private Label addClientPositionLabel;
    @FXML
    private Label addClientRegistrationCityLabel;
    @FXML
    private Label addClientCityLabel;
    @FXML
    private Label addClientAddressLabel;
    @FXML
    private Label addClientBirthDateLabel;
    @FXML
    private Label addClientBirthPlaceLabel;
    @FXML
    private Label addClientPassportSeriesLabel;
    @FXML
    private Label addClientPassportNumberLabel;
    @FXML
    private Label addClientIssuedByLabel;
    @FXML
    private Label addClientIssuedDateLabel;
    @FXML
    private Label addClientCitizenshipLabel;
    @FXML
    private Label addClientIDNumberLabel;
    @FXML
    private Label addClientMaritalStatusLabel;
    @FXML
    private Label addClientDisabilityLabel;
    @FXML
    private Label addClientRetireeLabel;
    @FXML
    private Label addClientHomePhoneLabel;
    @FXML
    private Label addClientMonthlyIncomeLabel;
    @FXML
    private Label addClientMobilePhoneLabel;
    @FXML
    private Label addClientEmailLabel;
    //ДАЛЬШЕ - Description
    @FXML
    private Label addClientNameDescription;
    @FXML
    private Label addClientSurnameDescription;
    @FXML
    private Label addClientPatronymicDescription;
    @FXML
    private Label addClientJobDescription;
    @FXML
    private Label addClientPositionDescription;
    @FXML
    private Label addClientRegistrationCityDescription;
    @FXML
    private Label addClientCityDescription;
    @FXML
    private Label addClientAddressDescription;
    @FXML
    private Label addClientPassportSeriesDescription;
    @FXML
    private Label addClientPassportNumberDescription;
    @FXML
    private Label addClientIssuedByDescription;
    @FXML
    private Label addClientIssuedDateDescription;
    @FXML
    private Label addClientBirthPlaceDescription;
    @FXML
    private Label addClientBirthDateDescription;
    @FXML
    private Label addClientCitizenshipDescription;
    @FXML
    private Label addClientIDNumberDescription;
    @FXML
    private Label addClientMaritalStatusDescription;
    @FXML
    private Label addClientDisabilityDescription;
    @FXML
    private Label addClientRetireeDescription;
    @FXML
    private Label addClientHomePhoneDescription;
    @FXML
    private Label addClientMonthlyIncomeDescription;
    @FXML
    private Label addClientMobilePhoneDescription;
    @FXML
    private Label addClientEmailDescription;
    @FXML
    private Label addClientLabel;
    @FXML
    private TextField addClientMobilePhoneTextField;
    @FXML
    private TextField addClientMonthlyIncomeTextField;
    @FXML
    private TextField addClientEmailTextField;
    @FXML
    private TextField addClientHomePhoneTextField;
    @FXML
    private DatePicker addClientIssuedDatePicker;
    @FXML
    private DatePicker addClientBirthDatePicker;
    @FXML
    private TextField addClientPassportSeriesTextField;
    @FXML
    private TextField addClientPassportNumberTextField;
    @FXML
    private TextField addClientBirthPlaceTextField;
    @FXML
    private TextField addClientCitizenshipTextField;
    @FXML
    private TextField addClientIssuedByTextField;
    @FXML
    private TextField addClientIDNumberTextField;
    @FXML
    private TextField addClientRegistrationCityTextField;
    @FXML
    private TextField addClientNameTextField;
    @FXML
    private TextField addClientPositionTextField;
    @FXML
    private TextField addClientPatronymicTextField;
    @FXML
    private TextField addClientJobTextField;
    @FXML
    private TextField addClientSurnameTextField;
    @FXML
    private TextField addClientCityTextField;
    @FXML
    private TextField addClientAddressTextField;
    @FXML
    private MenuButton addClientMaritalStatusMenuButton;
    private MaritalStatus addClientMaritalStatusValue;
    @FXML
    private MenuItem addClientMaritalStatusMenuItem_Single;
    @FXML
    private MenuItem addClientMaritalStatusMenuItem_Married;
    @FXML
    private MenuItem addClientMaritalStatusMenuItem_Divorced;
    @FXML
    private MenuButton addClientDisabilityMenuButton;
    private Disability addClientDisabilityValue;
    @FXML
    private MenuItem addClientDisabilityMenuItem_FirstGroup;
    @FXML
    private MenuItem addClientDisabilityMenuItem_SecondGroup;
    @FXML
    private MenuItem addClientDisabilityMenuItem_ThirdGroup;
    @FXML
    private MenuItem addClientDisabilityMenuItem_No;
    @FXML
    private MenuButton addClientRetireeMenuButton;
    private Retiree addClientRetireeValue;
    @FXML
    private MenuItem addClientRetireeMenuItem_Yes;
    @FXML
    private MenuItem addClientRetireeMenuItem_No;
    @FXML
    private Button addClientButton;
    //КОНЕЦ ФОРМЫ ДОБАВЛЕНИЯ КЛИНТА
    @FXML
    private TextField searchFieldClient;
    @FXML
    private Button searchButtonClient;
    @FXML
    private Button resetSearchButtonClient;
    @FXML
    private MenuButton criteriaButtonClient;
    @FXML
    private Menu criteriaClientMenuFIO;
    @FXML
    private MenuItem criteriaClientName;
    @FXML
    private MenuItem criteriaClientSurname;
    @FXML
    private MenuItem criteriaClientPatronymic;
    @FXML
    private MenuItem criteriaClientFIO;
    @FXML
    private Menu criteriaClientMenuPassport;
    @FXML
    private MenuItem criteriaClientPassportSeries;
    @FXML
    private MenuItem criteriaClientPassportNumber;
    @FXML
    private MenuItem criteriaClientIssuedBy;
    @FXML
    private MenuItem criteriaClientIssuedDate;
    @FXML
    private MenuItem criteriaClientBirthDate;
    @FXML
    private MenuItem criteriaClientBirthPlace;
    @FXML
    private MenuItem criteriaClientIDNumber;
    @FXML
    private MenuItem criteriaClientCitizenship;
    @FXML
    private Menu criteriaClientMenuResidence;
    @FXML
    private MenuItem criteriaClientActCity;
    @FXML
    private MenuItem criteriaClientActAddress;
    @FXML
    private MenuItem criteriaClientRegCity;
    @FXML
    private Menu criteriaClientMenuJob;
    @FXML
    private MenuItem criteriaClientJob;
    @FXML
    private MenuItem criteriaClientPosition;
    @FXML
    private Menu criteriaClientMenuContacts;
    @FXML
    private MenuItem criteriaClientEmail;
    @FXML
    private MenuItem criteriaClientHomePhone;
    @FXML
    private MenuItem criteriaClientMobilePhone;
    @FXML
    private Menu criteriaClientMenuOther;
    @FXML
    private MenuItem criteriaClientDisability;
    @FXML
    private MenuItem criteriaClientRetiree;
    @FXML
    private MenuItem criteriaClientMonthlyIncome;
    @FXML
    private MenuItem criteriaClientMaritalStatus;
    @FXML
    private MenuItem criteriaClientID;
    @FXML
    private AnchorPane menuPaneSettings;
    @FXML
    private AnchorPane menuPane31;
    @FXML
    private MenuButton languageButton;
    @FXML
    private MenuItem languageItem_Russian;
    @FXML
    private MenuItem languageItem_English;
    @FXML
    private Label languageLabel;
    @FXML
    private Label themeLabel;
    @FXML
    private MenuButton themeButton;
    @FXML
    private MenuItem themeItem_Dark;
    @FXML
    private MenuItem themeItem_Light;
    @FXML
    private Label customizationLabel;
    @FXML
    private AnchorPane menuPaneUserWriteIn;
    @FXML
    private AnchorPane menuPaneUserSubjects;
    @FXML
    private AnchorPane menuPaneUserTimeTable;
    @FXML
    private AnchorPane menuPaneUserNotifications;
    @FXML
    private AnchorPane menuPaneUserGraphs;
    @FXML
    private AnchorPane accountSettingsPane;
    @FXML
    private Label accountSettingsLabel;
    @FXML
    private TextField accountSettingsUsernameTextField;
    @FXML
    private PasswordField accountSettingsPasswordTextField;
    @FXML
    private TextField accountSettingsEmailTextField;
    @FXML
    private Label accountSettingsUsernameLabel;
    @FXML
    private Label accountSettingsPasswordLabel;
    @FXML
    private Label accountSettingsEmailLabel;
    @FXML
    private Button accountSettingsSaveButton;
    @FXML
    private Label settingsWarningLabel;
    @FXML
    private AnchorPane databaseSettingsPane;
    @FXML
    private Label databaseSettingsLabel;
    @FXML
    private TextField databaseSettingsURLTextField;
    @FXML
    private Label databaseSettingsURLLabel;
    @FXML
    private Label databaseSettingsUsernameLabel;
    @FXML
    private Label databaseSettingsPasswordLabel;
    @FXML
    private TextField databaseSettingsUsernameTextField;
    @FXML
    private PasswordField databaseSettingsPasswordTextField;
    @FXML
    private Label databaseSettingsConnectionStatusLabel;
    @FXML
    private ProgressIndicator databaseSettingsConnectionProgressIndicator;
    @FXML
    private Button connectionIndicator;
    @FXML
    private Button menuUserWriteInButton;
    @FXML
    private Button menuUserSubjectsButton;
    @FXML
    private Button menuUserTimeTableButton;
    @FXML
    private Button menuUserNotificationsButton;
    @FXML
    private Button menuUserGraphsButton;
    @FXML
    private Label menuPane1_DBLabel;
    @FXML
    private AnchorPane menuPaneInfo;
    @FXML
    private AnchorPane loginPane;
    @FXML
    private AnchorPane loginElementsPane;
    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginUsernameLabel;
    @FXML
    private Label loginPasswordLabel;
    @FXML
    private Button signUpButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label loginWarning;
    @FXML
    private Label titleLabel;


    public static void main(String[] args) {

        launch(args);
    }

    @FXML
    void initialize() throws SQLException {
        start = Instant.now();
        primaryAnchorPane.getStylesheets().add("CSS/DarkTheme.css");
        /*connDB =
                new DatabaseConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" +
                        "&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow", "root", "root");*/
        connServer = new ServerConnection("127.0.0.1", 8000);
        if (!connServer.exists()) {
            loginWarning.setStyle("-fx-text-fill: #d85751");
            loginWarning.setText("No connection.");
        } else
            serverIP = "127.0.0.1";


        //       initUsersData();
        //       initClientsData();


        /*idClientColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        patronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        passportSeriesColumn.setCellValueFactory(new PropertyValueFactory<>("passportSeries"));
        passportNumberColumn.setCellValueFactory(new PropertyValueFactory<>("passportNumber"));
        issuedByColumn.setCellValueFactory(new PropertyValueFactory<>("issuedBy"));
        issuedDateColumn.setCellValueFactory(new PropertyValueFactory<>("issuedDate"));
        birthPlaceColumn.setCellValueFactory(new PropertyValueFactory<>("birthPlace"));
        actualResidenceCityColumn.setCellValueFactory(new PropertyValueFactory<>("actualResidenceCity"));
        actualResidenceAddressColumn.setCellValueFactory(new PropertyValueFactory<>("actualResidenceAddress"));
        homeNumberColumn.setCellValueFactory(new PropertyValueFactory<>("homeNumber"));
        mobileNumberColumn.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        emailClientColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        jobColumn.setCellValueFactory(new PropertyValueFactory<>("job"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        registrationCityColumn.setCellValueFactory(new PropertyValueFactory<>("registrationCity"));
        citizenshipColumn.setCellValueFactory(new PropertyValueFactory<>("citizenship"));
        monthlyIncomeColumn.setCellValueFactory(new PropertyValueFactory<>("monthlyIncome"));
        idNumberColumn.setCellValueFactory(new PropertyValueFactory<>("idNumber"));
        clientsTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        */

        //patro, series, mob,
        groupLevelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        groupLevelColumn.setOnEditCommit((TableColumn.CellEditEvent<Group, String> t) -> {
            Group cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("[ABC]")) {
                cl.setLevelServer(connServer, t.getNewValue());
                groupsTable.requestFocus();
            } else {
                initGroupsDataServerBuffer();
            }
        });


        lessonGroupIdColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lessonGroupIdColumn.setOnEditCommit((TableColumn.CellEditEvent<Lesson, String> t) -> {
            Lesson cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("[0-9]+") || groupExists(Integer.parseInt(t.getNewValue().trim()))) {
                cl.setGroupIdServer(connServer, t.getNewValue());
                groupsTable.requestFocus();
            } else {
                initGroupsDataServerBuffer();
            }
        });
        lessonTeacherSubjectIdColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lessonTeacherSubjectIdColumn.setOnEditCommit((TableColumn.CellEditEvent<Lesson, String> t) -> {
            Lesson cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("[0-9]+") || teacherSubjectExists(Integer.parseInt(t.getNewValue().trim()))) {
                cl.setTeacherSubjectIdServer(connServer, t.getNewValue());
                groupsTable.requestFocus();
            } else {
                initGroupsDataServerBuffer();
            }
        });
        lessonCabinetColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lessonCabinetColumn.setOnEditCommit((TableColumn.CellEditEvent<Lesson, String> t) -> {
            Lesson cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("[0-9]+")) {
                cl.setCabinetServer(connServer, t.getNewValue());
                groupsTable.requestFocus();
            } else {
                initGroupsDataServerBuffer();
            }
        });
        lessonDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lessonDateColumn.setOnEditCommit((TableColumn.CellEditEvent<Lesson, String> t) -> {
            Lesson cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")) {
                cl.setDateServer(connServer, t.getNewValue());
                groupsTable.requestFocus();
            } else {
                initGroupsDataServerBuffer();
            }
        });
        lessonTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lessonTimeColumn.setOnEditCommit((TableColumn.CellEditEvent<Lesson, String> t) -> {
            Lesson cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("(?:[01]\\d|2[0123]):(?:[012345]\\d):(?:[012345]\\d)")) {
                cl.setTimeServer(connServer, t.getNewValue());
                groupsTable.requestFocus();
            } else {
                initGroupsDataServerBuffer();
            }
        });


        studentNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        studentNameColumn.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
            Student cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("[а-яА-Я]{2,20}")) {
                cl.setNameServer(connServer, t.getNewValue());
                groupsTable.requestFocus();
            } else {
                initGroupsDataServerBuffer();
            }
        });
        studentSurnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        studentSurnameColumn.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
            Student cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("[а-яА-Я]{2,20}")) {
                cl.setSurnameServer(connServer, t.getNewValue());
                groupsTable.requestFocus();
            } else {
                initGroupsDataServerBuffer();
            }
        });
        studentPatronymicColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        studentPatronymicColumn.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
            Student cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("[а-яА-Я]{2,30}")) {
                cl.setPatronymicServer(connServer, t.getNewValue());
                groupsTable.requestFocus();
            } else {
                initGroupsDataServerBuffer();
            }
        });
        studentGroupIdColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        studentGroupIdColumn.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
            Student cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("[0-9]+") || groupExists(Integer.parseInt(t.getNewValue().trim()))) {
                cl.setGroupIdServer(connServer, t.getNewValue());
                groupsTable.requestFocus();
            } else {
                initGroupsDataServerBuffer();
            }
        });
        studentEmailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        studentEmailColumn.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
            Student cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("(?:[a-z0-9!_-]+(?:\\.[a-z0-9!_-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+))") || t.getNewValue().equals("")) {
                cl.setEmailServer(connServer, t.getNewValue());
                groupsTable.requestFocus();
            } else {
                initGroupsDataServerBuffer();
            }
        });
        studentPhoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        studentPhoneColumn.setOnEditCommit((TableColumn.CellEditEvent<Student, String> t) -> {
            Student cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("^(\\+375|375)?[\\s\\-]?\\(?(17|29|33|44)\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$") || t.getNewValue().equals("")) {
                cl.setPhoneServer(connServer, t.getNewValue());
                groupsTable.requestFocus();
            } else {
                initGroupsDataServerBuffer();
            }
        });


        subjectNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        subjectNameColumn.setOnEditCommit((TableColumn.CellEditEvent<Subject, String> t) -> {
            Subject cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("[а-яА-Я]{2,40}")) {
                cl.setNameServer(connServer, t.getNewValue());
                groupsTable.requestFocus();
            } else {
                initGroupsDataServerBuffer();
            }
        });
        subjectHoursColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        subjectHoursColumn.setOnEditCommit((TableColumn.CellEditEvent<Subject, String> t) -> {
            Subject cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("[0-9]+")) {
                cl.setHoursServer(connServer, t.getNewValue());
                groupsTable.requestFocus();
            } else {
                initGroupsDataServerBuffer();
            }
        });


        teacherNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        teacherNameColumn.setOnEditCommit((TableColumn.CellEditEvent<Teacher, String> t) -> {
            Teacher cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("[а-яА-Я]{2,20}")) {
                cl.setNameServer(connServer, t.getNewValue());
                groupsTable.requestFocus();
            } else {
                initGroupsDataServerBuffer();
            }
        });
        teacherSurnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        teacherSurnameColumn.setOnEditCommit((TableColumn.CellEditEvent<Teacher, String> t) -> {
            Teacher cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("[а-яА-Я]{2,20}")) {
                cl.setSurnameServer(connServer, t.getNewValue());
                groupsTable.requestFocus();
            } else {
                initGroupsDataServerBuffer();
            }
        });
        teacherPatronymicColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        teacherPatronymicColumn.setOnEditCommit((TableColumn.CellEditEvent<Teacher, String> t) -> {
            Teacher cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("[а-яА-Я]{2,30}")) {
                cl.setPatronymicServer(connServer, t.getNewValue());
                groupsTable.requestFocus();
            } else {
                initGroupsDataServerBuffer();
            }
        });


        teacherSubjectTeacherIdColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        teacherSubjectTeacherIdColumn.setOnEditCommit((TableColumn.CellEditEvent<TeacherSubject, String> t) -> {
            TeacherSubject cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("[0-9]+") || teacherExists(Integer.parseInt(t.getNewValue().trim()))) {
                cl.setTeacherIdServer(connServer, t.getNewValue());
                groupsTable.requestFocus();
            } else {
                initGroupsDataServerBuffer();
            }
        });
        teacherSubjectSubjectIdColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        teacherSubjectSubjectIdColumn.setOnEditCommit((TableColumn.CellEditEvent<TeacherSubject, String> t) -> {
            TeacherSubject cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("[0-9]+") || subjectExists(Integer.parseInt(t.getNewValue().trim()))) {
                cl.setSubjectIdServer(connServer, t.getNewValue());
                groupsTable.requestFocus();
            } else {
                initGroupsDataServerBuffer();
            }
        });


        /*
        surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameColumn.setOnEditCommit((TableColumn.CellEditEvent<Client, String> t) -> {
            Client cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("[а-яА-Я]{2,20}") && isFullnameUnique(cl.getName(), t.getNewValue(), cl.getPatronymic())) {
                cl.setSurnameServer(connServer, t.getNewValue());
                clientsTable.requestFocus();
            } else {
                initClientsDataServerBuffer();
            }
        });
        patronymicColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        patronymicColumn.setOnEditCommit((TableColumn.CellEditEvent<Client, String> t) -> {
            Client cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("[а-яА-Я]{2,30}") && isFullnameUnique(cl.getName(), cl.getSurname(), t.getNewValue())) {
                cl.setPatronymicServer(connServer, t.getNewValue());
                clientsTable.requestFocus();
            } else {
                initClientsDataServerBuffer();
            }
        });
        birthDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        birthDateColumn.setOnEditCommit((TableColumn.CellEditEvent<Client, String> t) -> {
            Client cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("^\\d{4}[-/.](((0)[0-9])|((1)[0-2]))[-/.]([0-2][0-9]|(3)[0-1])$") && !LocalDate.parse(t.getNewValue()).isAfter(LocalDate.now())) {
                cl.setBirthDateServer(connServer, t.getNewValue());
                clientsTable.requestFocus();
            } else {
                initClientsDataServerBuffer();
            }
        });
        passportSeriesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passportSeriesColumn.setOnEditCommit((TableColumn.CellEditEvent<Client, String> t) -> {
            if (t.getNewValue().trim().matches("[a-zA-Z]{2}")) {
                (t.getTableView().getItems().get(t.getTablePosition().getRow())).setPassportSeriesServer(connServer, t.getNewValue());
                clientsTable.requestFocus();
            } else {
                initClientsDataServerBuffer();
            }
        });
        passportNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passportNumberColumn.setOnEditCommit((TableColumn.CellEditEvent<Client, String> t) -> {
            Client cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("^\\d{7}$") && isPassportNumberUnique(t.getNewValue())) {
                cl.setPassportNumberServer(connServer, t.getNewValue());
                clientsTable.requestFocus();
            } else {
                initClientsDataServerBuffer();
            }
        });
        issuedByColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        issuedByColumn.setOnEditCommit((TableColumn.CellEditEvent<Client, String> t) -> {
            if (t.getNewValue().trim().length() > 4) {
                (t.getTableView().getItems().get(t.getTablePosition().getRow())).setIssuedByServer(connServer, t.getNewValue());
                clientsTable.requestFocus();
            } else {
                initClientsDataServerBuffer();
            }
        });
        issuedDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        issuedDateColumn.setOnEditCommit((TableColumn.CellEditEvent<Client, String> t) -> {
            Client cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("^\\d{4}[-/.](((0)[0-9])|((1)[0-2]))[-/.]([0-2][0-9]|(3)[0-1])$") && !LocalDate.parse(t.getNewValue()).isAfter(LocalDate.now())) {
                cl.setIssuedDateServer(connServer, t.getNewValue());
                clientsTable.requestFocus();
            } else {
                initClientsDataServerBuffer();
            }
        });
        birthPlaceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        birthPlaceColumn.setOnEditCommit((TableColumn.CellEditEvent<Client, String> t) -> {
            if (t.getNewValue().trim().length() > 4) {
                (t.getTableView().getItems().get(t.getTablePosition().getRow())).setBirthPlaceServer(connServer, t.getNewValue());
                clientsTable.requestFocus();
            } else {
                initClientsDataServerBuffer();
            }
        });
        actualResidenceCityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        actualResidenceCityColumn.setOnEditCommit((TableColumn.CellEditEvent<Client, String> t) -> {
            if (t.getNewValue().trim().matches("[а-яА-Я]{2,20}")) {
                (t.getTableView().getItems().get(t.getTablePosition().getRow())).setActualResidenceCityServer(connServer, t.getNewValue());
                clientsTable.requestFocus();
            } else {
                initClientsDataServerBuffer();
            }
        });
        actualResidenceAddressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        actualResidenceAddressColumn.setOnEditCommit((TableColumn.CellEditEvent<Client, String> t) -> {
            if (t.getNewValue().trim().length() > 4) {
                (t.getTableView().getItems().get(t.getTablePosition().getRow())).setActualResidenceAddressServer(connServer, t.getNewValue());
                clientsTable.requestFocus();
            } else {
                initClientsDataServerBuffer();
            }
        });
        homeNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        homeNumberColumn.setOnEditCommit((TableColumn.CellEditEvent<Client, String> t) -> {
            if (t.getNewValue().trim().matches("^\\d{7}$")) {
                (t.getTableView().getItems().get(t.getTablePosition().getRow())).setHomeNumberServer(connServer, t.getNewValue());
                clientsTable.requestFocus();
            } else {
                initClientsDataServerBuffer();
            }
        });
        mobileNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        mobileNumberColumn.setOnEditCommit((TableColumn.CellEditEvent<Client, String> t) -> {
            if (t.getNewValue().trim().matches("^(\\+375|375)?[\\s\\-]?\\(?(17|29|33|44)\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$")) {
                (t.getTableView().getItems().get(t.getTablePosition().getRow())).setMobileNumberServer(connServer, t.getNewValue());
                clientsTable.requestFocus();
            } else {
                initClientsDataServerBuffer();
            }
        });
        emailClientColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailClientColumn.setOnEditCommit((TableColumn.CellEditEvent<Client, String> t) -> {
            if (t.getNewValue().trim().matches("(?:[a-z0-9!_-]+(?:\\.[a-z0-9!_-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+))")) {
                (t.getTableView().getItems().get(t.getTablePosition().getRow())).setEmailServer(connServer, t.getNewValue());
                clientsTable.requestFocus();
            } else {
                initClientsDataServerBuffer();
            }
        });
        jobColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        jobColumn.setOnEditCommit((TableColumn.CellEditEvent<Client, String> t) -> {
            if (t.getNewValue().trim().length() > 4) {
                (t.getTableView().getItems().get(t.getTablePosition().getRow())).setJobServer(connServer, t.getNewValue());
                clientsTable.requestFocus();
            } else {
                initClientsDataServerBuffer();
            }
        });
        positionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        positionColumn.setOnEditCommit((TableColumn.CellEditEvent<Client, String> t) -> {
            if (t.getNewValue().trim().length() > 0) {
                (t.getTableView().getItems().get(t.getTablePosition().getRow())).setPositionServer(connServer, t.getNewValue());
                clientsTable.requestFocus();
            } else {
                initClientsDataServerBuffer();
            }
        });
        registrationCityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        registrationCityColumn.setOnEditCommit((TableColumn.CellEditEvent<Client, String> t) -> {
            if (t.getNewValue().trim().length() > 4) {
                (t.getTableView().getItems().get(t.getTablePosition().getRow())).setRegistrationCityServer(connServer, t.getNewValue());
                clientsTable.requestFocus();
            } else {
                initClientsDataServerBuffer();
            }
        });
        citizenshipColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        citizenshipColumn.setOnEditCommit((TableColumn.CellEditEvent<Client, String> t) -> {
            if (t.getNewValue().trim().length() > 4) {
                (t.getTableView().getItems().get(t.getTablePosition().getRow())).setCitizenshipServer(connServer, t.getNewValue());
                clientsTable.requestFocus();
            } else {
                initClientsDataServerBuffer();
            }
        });
        monthlyIncomeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        monthlyIncomeColumn.setOnEditCommit((TableColumn.CellEditEvent<Client, String> t) -> {
            if (t.getNewValue().trim().matches("^[0-9]+(\\.[0-9]+)?$")) {
                (t.getTableView().getItems().get(t.getTablePosition().getRow())).setMonthlyIncomeServer(connServer, t.getNewValue());
                clientsTable.requestFocus();
            } else {
                initClientsDataServerBuffer();
            }
        });
        idNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        idNumberColumn.setOnEditCommit((TableColumn.CellEditEvent<Client, String> t) -> {
            Client cl = (t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if (t.getNewValue().trim().matches("[A-Z0-9]{14}") && isIDNumberUnique(t.getNewValue())) {
                cl.setIdNumberServer(connServer, t.getNewValue());
                clientsTable.requestFocus();
            } else {
                initClientsDataServerBuffer();
            }
        });

        clientsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);*/

        /*addClientNameDescription.setText("");
        addClientSurnameDescription.setText("");
        addClientPatronymicDescription.setText("");
        addClientJobDescription.setText("");
        addClientPositionDescription.setText("");
        addClientRegistrationCityDescription.setText("");
        addClientCityDescription.setText("");
        addClientAddressDescription.setText("");
        addClientPassportSeriesDescription.setText("");
        addClientPassportNumberDescription.setText("");
        addClientIssuedByDescription.setText("");
        addClientIssuedDateDescription.setText("");
        addClientBirthPlaceDescription.setText("");
        addClientBirthDateDescription.setText("");
        addClientCitizenshipDescription.setText("");
        addClientIDNumberDescription.setText("");
        addClientMaritalStatusDescription.setText("");
        addClientDisabilityDescription.setText("");
        addClientRetireeDescription.setText("");
        addClientHomePhoneDescription.setText("");
        addClientMonthlyIncomeDescription.setText("");
        addClientMobilePhoneDescription.setText("");
        addClientEmailDescription.setText("");
        addClientBirthDatePicker.setValue(LocalDate.now());
        addClientIssuedDatePicker.setValue(LocalDate.now());

        addClientNameDescription.getStyleClass().add("descriptionLabel");
        addClientSurnameDescription.getStyleClass().add("descriptionLabel");
        addClientPatronymicDescription.getStyleClass().add("descriptionLabel");
        addClientJobDescription.getStyleClass().add("descriptionLabel");
        addClientPositionDescription.getStyleClass().add("descriptionLabel");
        addClientRegistrationCityDescription.getStyleClass().add("descriptionLabel");
        addClientCityDescription.getStyleClass().add("descriptionLabel");
        addClientAddressDescription.getStyleClass().add("descriptionLabel");
        addClientPassportSeriesDescription.getStyleClass().add("descriptionLabel");
        addClientPassportNumberDescription.getStyleClass().add("descriptionLabel");
        addClientIssuedByDescription.getStyleClass().add("descriptionLabel");
        addClientIssuedDateDescription.getStyleClass().add("descriptionLabel");
        addClientBirthPlaceDescription.getStyleClass().add("descriptionLabel");
        addClientBirthDateDescription.getStyleClass().add("descriptionLabel");
        addClientCitizenshipDescription.getStyleClass().add("descriptionLabel");
        addClientIDNumberDescription.getStyleClass().add("descriptionLabel");
        addClientMaritalStatusDescription.getStyleClass().add("descriptionLabel");
        addClientDisabilityDescription.getStyleClass().add("descriptionLabel");
        addClientRetireeDescription.getStyleClass().add("descriptionLabel");
        addClientHomePhoneDescription.getStyleClass().add("descriptionLabel");
        addClientMonthlyIncomeDescription.getStyleClass().add("descriptionLabel");
        addClientMobilePhoneDescription.getStyleClass().add("descriptionLabel");
        addClientEmailDescription.getStyleClass().add("descriptionLabel");*/
        addButtonsToTable();


        //Дальше - функционал элементов
        loginWarning.getStyleClass().add("loginWarning");
        connectionIndicator.getStyleClass().add("connectionIndicator");
        connectionIndicator.setOnAction(actionEvent -> {
            initUsersDataServerBuffer();
        });

        title.getStyleClass().add("title");
        menuPane31.getStyleClass().add("elementsPane");
        accountSettingsPane.getStyleClass().add("elementsPane");
        databaseSettingsPane.getStyleClass().add("elementsPane");
        workPane.getStyleClass().add("workPane");
        loginPane.getStyleClass().add("loginPane");
        menuAdmin.getStyleClass().add("menu");
        menuUser.getStyleClass().add("menu");
        primaryAnchorPane.getStyleClass().add("primaryAnchorPane");
        primaryAnchorPane.setOnKeyPressed(keyEvent -> {
            if (currentUser != null) {
                switch (keyEvent.getCode()) {
                    case DIGIT1:
                        if (currentUser.getAccessMode() == 1)
                            menuAdminUsersButton.fire();
                        else
                            menuUserWriteInButton.fire();
                        break;
                    case DIGIT2:
                        if (currentUser.getAccessMode() == 1)
                            menuAdminLessonsButton.fire();
                        else
                            menuUserSubjectsButton.fire();
                        break;
                    case DIGIT3:
                        if (currentUser.getAccessMode() == 1)
                            menuAdminTeachersSubjectsButton.fire();
                        else
                            menuUserTimeTableButton.fire();
                        break;
                    case DIGIT4:
                        if (currentUser.getAccessMode() == 1)
                            menuAdminGroupsButton.fire();
                        else
                            menuUserNotificationsButton.fire();
                        break;
                    case DIGIT5:
                        if (currentUser.getAccessMode() == 1)
                            menuAdminStudentsButton.fire();
                        else
                            menuUserGraphsButton.fire();
                        break;
                    case DIGIT6:
                        if (currentUser.getAccessMode() == 1)
                            menuAdminTeachersButton.fire();
                        else
                            menuUserSettingsButton.fire();
                        break;
                    case DIGIT7:
                        if (currentUser.getAccessMode() == 1)
                            menuAdminSubjectsButton.fire();
                        else
                            menuUserInfoButton.fire();
                        break;
                    case DIGIT8:
                        if (currentUser.getAccessMode() == 1)
                            menuAdminSettingsButton.fire();
                        break;
                    case DIGIT9:
                        if (currentUser.getAccessMode() == 1)
                            menuAdminInfoButton.fire();
                        break;
                    case ESCAPE:
                        logoutButtonAdmin.fire();
                        break;
                }
            }
        });
        leftAnchorPane.getStyleClass().add("leftAnchorPane");
        rightAnchorPane.getStyleClass().add("rightAnchorPane");
        createUser_AnchorPane.getStyleClass().add("elementsPane");
        changeUser_AnchorPane.getStyleClass().add("elementsPane");
        deleteUser_AnchorPane.getStyleClass().add("elementsPane");
        /*createClient_AnchorPane.getStyleClass().add("elementsPane");
        createClient_AnchorPane_NameJobResidencePane.getStyleClass().add("oneBorderPane");
        createClient_AnchorPane_PassportDataPane.getStyleClass().add("oneBorderPane");
        clientManagementAnchorPane.getStyleClass().add("clientManagementAnchorPane");
        clientManagementScrollPane.getStyleClass().add("clientManagementScrollPane");
        clientManagementScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        clientManagementScrollPane.setMinWidth(550);
        clientManagementScrollPane.setMaxWidth(1920);*/

        lessonsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        teachersSubjectsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        groupsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        studentsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        teachersScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        subjectsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        lessonsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        teachersSubjectsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        groupsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        studentsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        teachersScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        subjectsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        lessonsAnchorPane.getStyleClass().add("clientManagementAnchorPane");
        lessonsScrollPane.getStyleClass().add("clientManagementScrollPane");
        teachersSubjectsAnchorPane.getStyleClass().add("clientManagementAnchorPane");
        teachersSubjectsScrollPane.getStyleClass().add("clientManagementScrollPane");
        groupsAnchorPane.getStyleClass().add("clientManagementAnchorPane");
        groupsScrollPane.getStyleClass().add("clientManagementScrollPane");
        studentsAnchorPane.getStyleClass().add("clientManagementAnchorPane");
        studentsScrollPane.getStyleClass().add("clientManagementScrollPane");
        teachersAnchorPane.getStyleClass().add("clientManagementAnchorPane");
        teachersScrollPane.getStyleClass().add("clientManagementScrollPane");
        subjectsAnchorPane.getStyleClass().add("clientManagementAnchorPane");
        subjectsScrollPane.getStyleClass().add("clientManagementScrollPane");

        hideButton.getStyleClass().add("hideButton");
        minimizeButton.getStyleClass().add("minimizeButton");
        exitButton.getStyleClass().add("exitButton");

        hideButton.setOnAction(actionEvent -> {
            Stage stage2 = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage2.setIconified(true);
        });
        minimizeButton.setOnAction(actionEvent -> minimize());
        exitButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            saveLastConfig();
            stage.close();
            System.exit(0);
        });

        languageButton.setFocusTraversable(false);
        themeButton.setFocusTraversable(false);
        hideButton.setFocusTraversable(false);
        minimizeButton.setFocusTraversable(false);
        exitButton.setFocusTraversable(false);
        logoutButtonAdmin.setFocusTraversable(false);
        logoutButtonUser.setFocusTraversable(false);
        lessonsScrollPane.setFocusTraversable(false);
        teachersSubjectsScrollPane.setFocusTraversable(false);
        groupsScrollPane.setFocusTraversable(false);
        studentsScrollPane.setFocusTraversable(false);
        teachersScrollPane.setFocusTraversable(false);
        subjectsScrollPane.setFocusTraversable(false);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        accessModeColumn.setCellValueFactory(new PropertyValueFactory<>("accessMode"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        groupIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        groupLevelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));

        lessonIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        lessonGroupIdColumn.setCellValueFactory(new PropertyValueFactory<>("groupId"));
        lessonTeacherSubjectIdColumn.setCellValueFactory(new PropertyValueFactory<>("teacher_subjectId"));
        lessonCabinetColumn.setCellValueFactory(new PropertyValueFactory<>("cabinet"));
        lessonDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        lessonTimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        studentPatronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        studentGroupIdColumn.setCellValueFactory(new PropertyValueFactory<>("groupId"));
        studentEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        studentPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        subjectIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        subjectNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        subjectHoursColumn.setCellValueFactory(new PropertyValueFactory<>("hours"));

        teacherIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        teacherNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        teacherSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        teacherPatronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));

        teacherSubjectIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        teacherSubjectTeacherIdColumn.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
        teacherSubjectSubjectIdColumn.setCellValueFactory(new PropertyValueFactory<>("subjectId"));


        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //USER
        menuUserWriteInButton.setOnAction(actionEvent -> selectMenuItem(menuUserWriteInButton, menuPaneUserWriteIn));
        menuUserSubjectsButton.setOnAction(actionEvent -> selectMenuItem(menuUserSubjectsButton, menuPaneUserSubjects));
        menuUserTimeTableButton.setOnAction(actionEvent -> selectMenuItem(menuUserTimeTableButton, menuPaneUserTimeTable));
        menuUserNotificationsButton.setOnAction(actionEvent -> selectMenuItem(menuUserNotificationsButton, menuPaneUserNotifications));
        menuUserGraphsButton.setOnAction(actionEvent -> selectMenuItem(menuUserGraphsButton, menuPaneUserGraphs));
        menuUserSettingsButton.setOnAction(actionEvent -> selectMenuItem(menuUserSettingsButton, menuPaneSettings));

        menuUserInfoButton.setOnAction(actionEvent -> selectMenuItem(menuUserInfoButton, menuPaneInfo));
        //ADMIN
        //1
        menuAdminUsersButton.setOnAction(actionEvent -> selectMenuItem(menuAdminUsersButton, menuPaneUsers));
        //2
        menuAdminLessonsButton.setOnAction(actionEvent -> selectMenuItem(menuAdminLessonsButton, menuPaneLessons));
        //3
        menuAdminTeachersSubjectsButton.setOnAction(actionEvent -> selectMenuItem(menuAdminTeachersSubjectsButton, menuPaneTeachersSubjects));
        //4
        menuAdminGroupsButton.setOnAction(actionEvent -> selectMenuItem(menuAdminGroupsButton, menuPaneGroups));
        //5
        menuAdminStudentsButton.setOnAction(actionEvent -> selectMenuItem(menuAdminStudentsButton, menuPaneStudents));
        //6
        menuAdminTeachersButton.setOnAction(actionEvent -> selectMenuItem(menuAdminTeachersButton, menuPaneTeachers));
        //7
        menuAdminSubjectsButton.setOnAction(actionEvent -> selectMenuItem(menuAdminSubjectsButton, menuPaneSubjects));
        //8
        menuAdminSettingsButton.setOnAction(actionEvent -> selectMenuItem(menuAdminSettingsButton, menuPaneSettings));
        //9
        menuAdminInfoButton.setOnAction(actionEvent -> selectMenuItem(menuAdminInfoButton, menuPaneInfo));

        menuPaneUsers.setOnMouseClicked(mouseEvent -> {
            menuPaneUsers.requestFocus();
            usersTable.getSelectionModel().clearSelection();
        });
        menuPaneSettings.setOnMouseClicked(mouseEvent -> menuPaneSettings.requestFocus());
        menuPaneInfo.setOnMouseClicked(mouseEvent -> menuPaneInfo.requestFocus());
        loginPane.setOnMouseClicked(mouseEvent -> loginPane.requestFocus());

        menuPaneUsers.getStyleClass().add("menuPane");
        menuPaneSettings.getStyleClass().add("menuPane");
        menuPaneInfo.getStyleClass().add("menuPane");

        signUpButton.getStyleClass().add("signUpButton");
        loginButton.getStyleClass().add("loginButton");
        logoutButtonAdmin.getStyleClass().add("logoutButton");
        logoutButtonUser.getStyleClass().add("logoutButton");
        usersTable.getStyleClass().add("usersTable");
        groupsTable.getStyleClass().add("usersTable");
        lessonsTable.getStyleClass().add("usersTable");
        studentsTable.getStyleClass().add("usersTable");
        subjectsTable.getStyleClass().add("usersTable");
        teachersSubjectsTable.getStyleClass().add("usersTable");
        teachersTable.getStyleClass().add("usersTable");

        loginPane.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER && !(usernameField.getText().equals("") || passwordField.getText().equals("")))
                loginButton.fire();
            if (keyEvent.getCode() == KeyCode.ESCAPE)
                exitButton.fire();
        });
        usernameField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                passwordField.requestFocus();
                passwordField.selectAll();
            }
        });
        passwordField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                usernameField.requestFocus();
                usernameField.selectAll();
            }
        });

        changeUser_AnchorPane_Id.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                changeUser_AnchorPane_IdSubmitButton.fire();
        });
        changeUser_AnchorPane_Id.textProperty().addListener((observable, oldValue, newValue) ->
                changeUser_AnchorPane_Id.setStyle("-fx-border-color: transparent"));
        changeUser_AnchorPane_Id.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            System.out.println(oldPropertyValue + " -> " + newPropertyValue);
            if (!newPropertyValue)
                if (!StringUtils.isStrictlyNumeric(changeUser_AnchorPane_Id.getText()) && !changeUser_AnchorPane_Id.getText().equals(""))
                    changeUser_AnchorPane_Id.setStyle("-fx-border-color: rgb(255,13,19)");
        });


        changeUser_AnchorPane_Username.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                changeUserButton.fire();
            changeUser_AnchorPane_Username.setStyle("-fx-border-color: transparent");
        });
        changeUser_AnchorPane_Username.textProperty().addListener((observable, oldValue, newValue) -> changeUser_AnchorPane_Username.setStyle("-fx-border-color: transparent"));
        changeUser_AnchorPane_Username.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            System.out.println(oldPropertyValue + " -> " + newPropertyValue);
            if (!newPropertyValue)
                checkUsernamePassword(changeUser_AnchorPane_Username);
        });


        changeUser_AnchorPane_Password.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                changeUserButton.fire();
            changeUser_AnchorPane_Password.setStyle("-fx-border-color: transparent");
        });
        changeUser_AnchorPane_Password.textProperty().addListener((observable, oldValue, newValue) -> changeUser_AnchorPane_Password.setStyle("-fx-border-color: transparent"));
        changeUser_AnchorPane_Password.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            System.out.println(oldPropertyValue + " -> " + newPropertyValue);
            if (!newPropertyValue)
                checkUsernamePassword(changeUser_AnchorPane_Password);
        });

        changeUser_AnchorPane_Email.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                changeUserButton.fire();
            changeUser_AnchorPane_Email.setStyle("-fx-border-color: transparent");
        });
        changeUser_AnchorPane_Email.textProperty().addListener((observable, oldValue, newValue) -> changeUser_AnchorPane_Email.setStyle("-fx-border-color: transparent"));
        changeUser_AnchorPane_Email.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            System.out.println(oldPropertyValue + " -> " + newPropertyValue);
            if (!newPropertyValue)
                checkEmail(changeUser_AnchorPane_Email);
        });

        createUser_AnchorPane_Username.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                createUserButton.fire();
            createUser_AnchorPane_Username.setStyle("-fx-border-color: transparent");
        });
        createUser_AnchorPane_Username.textProperty().addListener((observable, oldValue, newValue) -> createUser_AnchorPane_Username.setStyle("-fx-border-color: transparent"));
        createUser_AnchorPane_Username.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            System.out.println(oldPropertyValue + " -> " + newPropertyValue);
            if (!newPropertyValue)
                checkUsernamePassword(createUser_AnchorPane_Username);
        });

        createUser_AnchorPane_Password.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                createUserButton.fire();
            createUser_AnchorPane_Password.setStyle("-fx-border-color: transparent");
        });
        createUser_AnchorPane_Password.textProperty().addListener((observable, oldValue, newValue) -> createUser_AnchorPane_Password.setStyle("-fx-border-color: transparent"));
        createUser_AnchorPane_Password.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            System.out.println(oldPropertyValue + " -> " + newPropertyValue);
            if (!newPropertyValue)
                checkUsernamePassword(createUser_AnchorPane_Password);
        });

        createUser_AnchorPane_Email.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                createUserButton.fire();
            createUser_AnchorPane_Email.setStyle("-fx-border-color: transparent");
        });
        createUser_AnchorPane_Email.textProperty().addListener((observable, oldValue, newValue) -> createUser_AnchorPane_Email.setStyle("-fx-border-color: transparent"));
        createUser_AnchorPane_Email.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            System.out.println(oldPropertyValue + " -> " + newPropertyValue);
            if (!newPropertyValue)
                checkEmail(createUser_AnchorPane_Email);
        });

        deleteUserTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                deleteUserButton.fire();
            deleteUserTextField.setStyle("-fx-border-color: transparent");
        });
        deleteUserTextField.textProperty().addListener((observable, oldValue, newValue) -> deleteUserTextField.setStyle("-fx-border-color: transparent"));
        deleteUserTextField.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            System.out.println(oldPropertyValue + " -> " + newPropertyValue);
            if (!newPropertyValue)
                checkDeleteField(deleteUserTextField);
        });

        accountSettingsSaveButton.setOnAction(actionEvent -> changeAccountData());
        accountSettingsUsernameTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                accountSettingsSaveButton.fire();
            }
        });
        accountSettingsPasswordTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                accountSettingsSaveButton.fire();
            }
        });
        accountSettingsEmailTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                accountSettingsSaveButton.fire();
            }
        });

        loginButton.setOnAction(actionEvent -> {
            System.out.println(serverIP);
            if (!connServer.exists()) {
                loginButton.setDisable(true);
                signUpButton.setDisable(true);
                connServer = new ServerConnection(serverIP, 8000);

                if (connServer.exists()) {
                    loginWarning.setStyle("-fx-text-fill: #7f8e55");
                    loginWarning.setText("Connection established.");
                } else {
                    loginWarning.setStyle("-fx-text-fill: #d85751");
                    loginWarning.setText("No connection.");
                }
                if (connServer.exists())
                    new Thread() {
                        @Override
                        public void run() {
                            initDataFromServer();
                        }
                    }.start();
            } else {
                loginWarning.setStyle("-fx-text-fill: #d85751");
                boolean was = false;
                String enteredUsername = usernameField.getText();
                String enteredPassword;
                MessageDigest digest = null;
                try {
                    digest = MessageDigest.getInstance("SHA-256");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    return;
                }
                byte[] hash = digest.digest(passwordField.getText().trim().getBytes(StandardCharsets.UTF_8));
                enteredPassword = Base64.getEncoder().encodeToString(hash);

                if (!(enteredPassword.length() < 3 || enteredUsername.length() < 3)) {
                    if (connServer.exists()) {
                        for (User u : usersData)
                            if (enteredUsername.equals(u.getUsername()) && enteredPassword.equals(u.getPassword())) {
                                was = true;
                                currentUser = u;
                                break;
                            }

                        if (was) {
                            try {
                                loginSuccess();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } else
                            loginWarning.setText("Wrong login/password.");
                    } else
                        loginWarning.setText("No connection.");
                } else
                    loginWarning.setText("Username/password must be at least 3 characters");
            }
            loginButton.setDisable(false);
            signUpButton.setDisable(false);
        });
        signUpButton.setOnAction(actionEvent -> {
            if (!connServer.exists()) {
                loginButton.setDisable(true);
                signUpButton.setDisable(true);
                connServer = new ServerConnection("serverIP", 8000);
                if (connServer.exists()) {
                    loginWarning.setStyle("-fx-text-fill: #7f8e55");
                    loginWarning.setText("Connection established.");
                } else {
                    loginWarning.setStyle("-fx-text-fill: #d85751");
                    loginWarning.setText("No connection.");
                }
                if (connServer.exists())
                    new Thread() {
                        @Override
                        public void run() {
                            initDataFromServer();
                        }
                    }.start();
            } else {
                new Thread() {
                    @Override
                    public void run() {
                        initDataFromServer();
                    }
                }.start();
                registration_User(loginWarning, usernameField.getText(), passwordField.getText());
            }
            loginButton.setDisable(false);
            signUpButton.setDisable(false);
        });
        logoutButtonAdmin.setOnAction(actionEvent -> {
            saveLastConfig();
            usernameField.clear();
            passwordField.clear();
            loginBegin();
        });
        logoutButtonUser.setOnAction(actionEvent -> {
            saveLastConfig();
            usernameField.clear();
            passwordField.clear();
            loginBegin();
        });
        changeUser_AnchorPane_IdSubmitButton.setOnAction(actionEvent -> submitId());
        changeUserButton.setOnAction(actionEvent -> {
            System.out.println(changeUser_AnchorPane_Username.getText() + " " + changeUser_AnchorPane_Password.getText());
            checkEmail(changeUser_AnchorPane_Email);
            checkUsernamePassword(changeUser_AnchorPane_Username);
            checkUsernamePassword(changeUser_AnchorPane_Password);
            if (checkEmail(changeUser_AnchorPane_Email) && checkUsernamePassword(changeUser_AnchorPane_Username) && checkUsernamePassword(changeUser_AnchorPane_Password))
                changeUser();
        });
        createUserButton.setOnAction(actionEvent -> {
            checkEmail(createUser_AnchorPane_Email);
            checkUsernamePassword(createUser_AnchorPane_Username);
            checkUsernamePassword(createUser_AnchorPane_Password);
            if (checkEmail(createUser_AnchorPane_Email) && checkUsernamePassword(createUser_AnchorPane_Username) && checkUsernamePassword(createUser_AnchorPane_Password))
                registration_Admin(null, createUser_AnchorPane_Username.getText(), createUser_AnchorPane_Password.getText(), createUser_AnchorPane_Email.getText(), createUser_AnchorPane_AccessMode_MenuButton.getText());
        });
        deleteUserButton.setOnAction(actionEvent -> {
            if (checkDeleteField(deleteUserTextField))
                deleteUsers();
        });

        criteriaMenuItem_Id.setOnAction(actionEvent -> criteriaButton.setText("ID"));
        criteriaMenuItem_Access.setOnAction(actionEvent -> criteriaButton.setText("Доступ"));
        criteriaMenuItem_Username.setOnAction(actionEvent -> criteriaButton.setText("Имя"));
        criteriaMenuItem_Password.setOnAction(actionEvent -> criteriaButton.setText("Пароль"));
        criteriaMenuItem_Email.setOnAction(actionEvent -> criteriaButton.setText("Эл. почта"));

        criteriaLessonId.setOnAction(actionEvent -> criteriaButtonLesson.setText(criteriaLessonId.getText()));
        criteriaLessonGroupId.setOnAction(actionEvent -> criteriaButtonLesson.setText(criteriaLessonGroupId.getText()));
        criteriaLessonTeacherSubjectId.setOnAction(actionEvent -> criteriaButtonLesson.setText(criteriaLessonTeacherSubjectId.getText()));
        criteriaLessonCebinet.setOnAction(actionEvent -> criteriaButtonLesson.setText(criteriaLessonCebinet.getText()));
        criteriaLessonDate.setOnAction(actionEvent -> criteriaButtonLesson.setText(criteriaLessonDate.getText()));
        criteriaLessonTime.setOnAction(actionEvent -> criteriaButtonLesson.setText(criteriaLessonTime.getText()));

        criteriaTeacherSubjectId.setOnAction(actionEvent -> criteriaButtonTeacherSubject.setText(criteriaTeacherSubjectId.getText()));
        criteriaTeacherSubjectTeacherId.setOnAction(actionEvent -> criteriaButtonTeacherSubject.setText(criteriaTeacherSubjectTeacherId.getText()));
        criteriaTeacherSubjectSubjectId.setOnAction(actionEvent -> criteriaButtonTeacherSubject.setText(criteriaTeacherSubjectSubjectId.getText()));

        criteriaGroupId.setOnAction(actionEvent -> criteriaButtonGroup.setText(criteriaGroupId.getText()));
        criteriaGroupLevel.setOnAction(actionEvent -> criteriaButtonGroup.setText(criteriaGroupLevel.getText()));

        criteriaStudentId.setOnAction(actionEvent -> criteriaButtonStudent.setText(criteriaStudentId.getText()));
        criteriaStudentName.setOnAction(actionEvent -> criteriaButtonStudent.setText(criteriaStudentName.getText()));
        criteriaStudentSurname.setOnAction(actionEvent -> criteriaButtonStudent.setText(criteriaStudentSurname.getText()));
        criteriaStudentPatronymic.setOnAction(actionEvent -> criteriaButtonStudent.setText(criteriaStudentPatronymic.getText()));
        criteriaStudentGroupId.setOnAction(actionEvent -> criteriaButtonStudent.setText(criteriaStudentGroupId.getText()));
        criteriaStudentEmail.setOnAction(actionEvent -> criteriaButtonStudent.setText(criteriaStudentEmail.getText()));
        criteriaStudentPhone.setOnAction(actionEvent -> criteriaButtonStudent.setText(criteriaStudentPhone.getText()));

        criteriaTeacherId.setOnAction(actionEvent -> criteriaButtonTeacher.setText(criteriaTeacherId.getText()));
        criteriaTeacherName.setOnAction(actionEvent -> criteriaButtonTeacher.setText(criteriaTeacherName.getText()));
        criteriaTeacherSurname.setOnAction(actionEvent -> criteriaButtonTeacher.setText(criteriaTeacherSurname.getText()));
        criteriaTeacherPatronymic.setOnAction(actionEvent -> criteriaButtonTeacher.setText(criteriaTeacherPatronymic.getText()));

        criteriaSubjectId.setOnAction(actionEvent -> criteriaButtonSubject.setText(criteriaSubjectId.getText()));
        criteriaSubjectName.setOnAction(actionEvent -> criteriaButtonSubject.setText(criteriaSubjectName.getText()));
        criteriaSubjectHours.setOnAction(actionEvent -> criteriaButtonSubject.setText(criteriaSubjectHours.getText()));


        /*criteriaClientName.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientName.getText()));
        criteriaClientSurname.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientSurname.getText()));
        criteriaClientPatronymic.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientPatronymic.getText()));
        criteriaClientFIO.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientFIO.getText()));
        criteriaClientPassportSeries.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientPassportSeries.getText()));
        criteriaClientPassportNumber.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientPassportNumber.getText()));
        criteriaClientIssuedBy.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientIssuedBy.getText()));
        criteriaClientIssuedDate.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientIssuedDate.getText()));
        criteriaClientBirthDate.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientBirthDate.getText()));
        criteriaClientBirthPlace.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientBirthPlace.getText()));
        criteriaClientActCity.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientActCity.getText()));
        criteriaClientActAddress.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientActAddress.getText()));
        criteriaClientRegCity.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientRegCity.getText()));
        criteriaClientJob.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientJob.getText()));
        criteriaClientPosition.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientPosition.getText()));
        criteriaClientEmail.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientEmail.getText()));
        criteriaClientHomePhone.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientHomePhone.getText()));
        criteriaClientMobilePhone.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientMobilePhone.getText()));
        criteriaClientDisability.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientDisability.getText()));
        criteriaClientRetiree.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientRetiree.getText()));
        criteriaClientMonthlyIncome.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientMonthlyIncome.getText()));
        criteriaClientIDNumber.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientIDNumber.getText()));
        criteriaClientMaritalStatus.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientMaritalStatus.getText()));
        criteriaClientCitizenship.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientCitizenship.getText()));
        criteriaClientID.setOnAction(actionEvent -> criteriaButtonClient.setText(criteriaClientID.getText()));
*/
        createUser_AccessMenuItem_User.setOnAction(actionEvent -> {
            if (currentLanguage.equals("English"))
                createUser_AnchorPane_AccessMode_MenuButton.setText("User");
            if (currentLanguage.equals("Russian"))
                createUser_AnchorPane_AccessMode_MenuButton.setText("Пользователь");
        });
        createUser_AccessMenuItem_Admin.setOnAction(actionEvent -> {
            if (currentLanguage.equals("English"))
                createUser_AnchorPane_AccessMode_MenuButton.setText("Admin");
            if (currentLanguage.equals("Russian"))
                createUser_AnchorPane_AccessMode_MenuButton.setText("Администратор");
        });
        changeUser_AccessMenuItem_User.setOnAction(actionEvent -> {
            if (currentLanguage.equals("English"))
                changeUser_AnchorPane_AccessMode_MenuButton.setText("User");
            if (currentLanguage.equals("Russian"))
                changeUser_AnchorPane_AccessMode_MenuButton.setText("Пользователь");
        });
        changeUser_AccessMenuItem_Admin.setOnAction(actionEvent -> {
            if (currentLanguage.equals("English"))
                changeUser_AnchorPane_AccessMode_MenuButton.setText("Admin");
            if (currentLanguage.equals("Russian"))
                changeUser_AnchorPane_AccessMode_MenuButton.setText("Администратор");
        });

        /*addClientMaritalStatusMenuButton.setOnAction(actionEvent -> addClientMaritalStatusMenuButton.setText(addClientMaritalStatusMenuButton.getText()));
        addClientDisabilityMenuButton.setOnAction(actionEvent -> addClientDisabilityMenuButton.setText(criteriaClientID.getText()));
        addClientRetireeMenuButton.setOnAction(actionEvent -> addClientRetireeMenuButton.setText(criteriaClientID.getText()));

        addClientMaritalStatusMenuItem_Single.setOnAction(actionEvent -> {
            addClientMaritalStatusMenuButton.setText(addClientMaritalStatusMenuItem_Single.getText());
            addClientMaritalStatusValue = MaritalStatus.Single;
        });
        addClientMaritalStatusMenuItem_Married.setOnAction(actionEvent -> {
            addClientMaritalStatusMenuButton.setText(addClientMaritalStatusMenuItem_Married.getText());
            addClientMaritalStatusValue = MaritalStatus.Married;
        });
        addClientMaritalStatusMenuItem_Divorced.setOnAction(actionEvent -> {
            addClientMaritalStatusMenuButton.setText(addClientMaritalStatusMenuItem_Divorced.getText());
            addClientMaritalStatusValue = MaritalStatus.Divorced;
        });
        addClientDisabilityMenuItem_FirstGroup.setOnAction(actionEvent -> {
            addClientDisabilityMenuButton.setText(addClientDisabilityMenuItem_FirstGroup.getText());
            addClientDisabilityValue = Disability.First_group;
        });
        addClientDisabilityMenuItem_SecondGroup.setOnAction(actionEvent -> {
            addClientDisabilityMenuButton.setText(addClientDisabilityMenuItem_SecondGroup.getText());
            addClientDisabilityValue = Disability.Second_group;
        });
        addClientDisabilityMenuItem_ThirdGroup.setOnAction(actionEvent -> {
            addClientDisabilityMenuButton.setText(addClientDisabilityMenuItem_ThirdGroup.getText());
            addClientDisabilityValue = Disability.Third_group;
        });
        addClientDisabilityMenuItem_No.setOnAction(actionEvent -> {
            addClientDisabilityMenuButton.setText(addClientDisabilityMenuItem_No.getText());
            addClientDisabilityValue = Disability.No;
        });
        addClientRetireeMenuItem_Yes.setOnAction(actionEvent -> {
            addClientRetireeMenuButton.setText(addClientRetireeMenuItem_Yes.getText());
            addClientRetireeValue = Retiree.Yes;
        });
        addClientRetireeMenuItem_No.setOnAction(actionEvent -> {
            addClientRetireeMenuButton.setText(addClientRetireeMenuItem_No.getText());
            addClientRetireeValue = Retiree.No;
        });

        addClientMobilePhoneTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientMobilePhoneTextField.setStyle("-fx-border-color: transparent");
            addClientMobilePhoneDescription.setText("");
        });
        addClientMonthlyIncomeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientMonthlyIncomeTextField.setStyle("-fx-border-color: transparent");
            addClientMonthlyIncomeDescription.setText("");
        });
        addClientEmailTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientEmailTextField.setStyle("-fx-border-color: transparent");
            addClientEmailDescription.setText("");
        });
        addClientHomePhoneTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientHomePhoneTextField.setStyle("-fx-border-color: transparent");
            addClientHomePhoneDescription.setText("");
        });
        addClientPassportSeriesTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientPassportSeriesTextField.setStyle("-fx-border-color: transparent");
            addClientPassportSeriesDescription.setText("");
        });
        addClientPassportNumberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientPassportNumberTextField.setStyle("-fx-border-color: transparent");
            addClientPassportNumberDescription.setText("");
        });
        addClientBirthPlaceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientBirthPlaceTextField.setStyle("-fx-border-color: transparent");
            addClientBirthPlaceDescription.setText("");
        });
        addClientCitizenshipTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientCitizenshipTextField.setStyle("-fx-border-color: transparent");
            addClientCitizenshipDescription.setText("");
        });
        addClientIssuedByTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientIssuedByTextField.setStyle("-fx-border-color: transparent");
            addClientIssuedByDescription.setText("");
        });
        addClientIDNumberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientIDNumberTextField.setStyle("-fx-border-color: transparent");
            addClientIDNumberDescription.setText("");
        });
        addClientRegistrationCityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientRegistrationCityTextField.setStyle("-fx-border-color: transparent");
            addClientRegistrationCityDescription.setText("");
        });
        addClientNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientNameTextField.setStyle("-fx-border-color: transparent");
            addClientNameDescription.setText("");
        });
        addClientPositionTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientPositionTextField.setStyle("-fx-border-color: transparent");
            addClientPositionDescription.setText("");
        });
        addClientPatronymicTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientPatronymicTextField.setStyle("-fx-border-color: transparent");
            addClientPatronymicDescription.setText("");
        });
        addClientJobTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientJobTextField.setStyle("-fx-border-color: transparent");
            addClientJobDescription.setText("");
        });
        addClientSurnameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientSurnameTextField.setStyle("-fx-border-color: transparent");
            addClientSurnameDescription.setText("");
        });
        addClientCityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientCityTextField.setStyle("-fx-border-color: transparent");
            addClientCityDescription.setText("");
        });
        addClientAddressTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            addClientAddressTextField.setStyle("-fx-border-color: transparent");
            addClientAddressDescription.setText("");
        });

        addClientMobilePhoneTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                addClientButton.fire();
        });
        addClientMonthlyIncomeTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                addClientButton.fire();
        });
        addClientEmailTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                addClientButton.fire();
        });
        addClientHomePhoneTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                addClientButton.fire();
        });
        addClientPassportSeriesTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                addClientButton.fire();
        });
        addClientPassportNumberTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                addClientButton.fire();
        });
        addClientBirthPlaceTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                addClientButton.fire();
        });
        addClientCitizenshipTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                addClientButton.fire();
        });
        addClientIssuedByTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                addClientButton.fire();
        });
        addClientIDNumberTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                addClientButton.fire();
        });
        addClientRegistrationCityTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                addClientButton.fire();
        });
        addClientNameTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                addClientButton.fire();
        });
        addClientPositionTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                addClientButton.fire();
        });
        addClientPatronymicTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                addClientButton.fire();
        });
        addClientJobTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                addClientButton.fire();
        });
        addClientSurnameTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                addClientButton.fire();
        });
        addClientCityTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                addClientButton.fire();
        });
        addClientAddressTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                addClientButton.fire();
        });

        addClientButton.setOnAction(actionEvent -> addClient());
*/
        addLessonButton.setOnAction(actionEvent -> addLesson());
        addTeacherSubjectButton.setOnAction(actionEvent -> addTeacherSubject());
        addGroupButton.setOnAction(actionEvent -> addGroup());
        addStudentButton.setOnAction(actionEvent -> addStudent());
        addTeacherButton.setOnAction(actionEvent -> addTeacher());
        addSubjectButton.setOnAction(actionEvent -> addSubject());

        languageItem_English.setOnAction(actionEvent -> {
            try {
                if (currentUser != null)
                    currentUser.setLanguageServer(connServer, "English");
                translate("Russian");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        languageItem_Russian.setOnAction(actionEvent -> {
            try {
                if (currentUser != null)
                    currentUser.setLanguageServer(connServer, "Russian");
                translate("Russian");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        themeItem_Dark.setOnAction(actionEvent -> {
            if (currentUser != null)
                currentUser.setThemeServer(connServer, "Dark");
            setTheme("Dark");
        });
        themeItem_Light.setOnAction(actionEvent -> {
            if (currentUser != null)
                currentUser.setThemeServer(connServer, "Light");
            setTheme("Dark");
        });
        themeItem_Light.setDisable(false);

        resetSearchButton.getStyleClass().add("resetSearchButton");
        resetButtonLessons.getStyleClass().add("resetSearchButton");
        resetButtonTeachersSubjects.getStyleClass().add("resetSearchButton");
        resetButtonGroups.getStyleClass().add("resetSearchButton");
        resetButtonStudents.getStyleClass().add("resetSearchButton");
        resetButtonTeachers.getStyleClass().add("resetSearchButton");
        resetButtonSubjects.getStyleClass().add("resetSearchButton");
        /*resetSearchButtonClient.getStyleClass().add("resetSearchButton");
        resetSearchButtonClient.setOnAction(actionEvent -> {
            searchFieldClient.clear();
            new Thread() {
                @Override
                public void run() {
                    initDataFromServer();
                }
            }.start();
        });*/
        resetSearchButton.setOnAction(actionEvent -> {
            searchField.clear();
            new Thread() {
                @Override
                public void run() {
                    initDataFromServer();
                }
            }.start();
        });
        resetButtonLessons.setOnAction(actionEvent -> {
            searchFieldLesson.clear();
            new Thread() {
                @Override
                public void run() {
                    initDataFromServer();
                }
            }.start();
        });
        resetButtonTeachersSubjects.setOnAction(actionEvent -> {
            searchFieldTeacherSubject.clear();
            new Thread() {
                @Override
                public void run() {
                    initDataFromServer();
                }
            }.start();
        });
        resetButtonGroups.setOnAction(actionEvent -> {
            searchFieldGroup.clear();
            new Thread() {
                @Override
                public void run() {
                    initDataFromServer();
                }
            }.start();
        });
        resetButtonStudents.setOnAction(actionEvent -> {
            searchFieldStudent.clear();
            new Thread() {
                @Override
                public void run() {
                    initDataFromServer();
                }
            }.start();
        });
        resetButtonTeachers.setOnAction(actionEvent -> {
            searchFieldTeacher.clear();
            new Thread() {
                @Override
                public void run() {
                    initDataFromServer();
                }
            }.start();
        });
        resetButtonSubjects.setOnAction(actionEvent -> {
            searchFieldSubject.clear();
            new Thread() {
                @Override
                public void run() {
                    initDataFromServer();
                }
            }.start();
        });

        searchField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                searchButton.fire();
        });
        searchFieldLesson.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                searchButtonLesson.fire();
        });
        searchFieldTeacherSubject.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                searchButtonTeacherSubject.fire();
        });
        searchFieldGroup.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                searchButtonGroup.fire();
        });
        searchFieldStudent.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                searchButtonStudent.fire();
        });
        searchFieldTeacher.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                searchButtonTeacher.fire();
        });
        searchFieldSubject.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                searchButtonSubject.fire();
        });

        searchButton.setOnAction(actionEvent -> searchUser());
        searchButtonLesson.setOnAction(actionEvent -> searchLesson());
        searchButtonTeacherSubject.setOnAction(actionEvent -> searchTeacherSubject());
        searchButtonGroup.setOnAction(actionEvent -> searchGroup());
        searchButtonStudent.setOnAction(actionEvent -> searchStudent());
        searchButtonTeacher.setOnAction(actionEvent -> searchTeacher());
        searchButtonSubject.setOnAction(actionEvent -> searchSubject());

        /*searchButtonClient.setOnAction(actionEvent -> searchClient());*/

        //#######################################################################УБРАТЬ
        /*addClientMobilePhoneTextField.setText("");
        addClientMonthlyIncomeTextField.setText("");
        addClientEmailTextField.setText("");
        addClientHomePhoneTextField.setText("");
        addClientPassportSeriesTextField.setText("MP");
        addClientPassportNumberTextField.setText("3418583");
        addClientBirthPlaceTextField.setText("Минск");
        addClientCitizenshipTextField.setText("Беларусь");
        addClientIssuedByTextField.setText("Орган выдачи");
        addClientIDNumberTextField.setText("6632915P119PB4");
        addClientRegistrationCityTextField.setText("Минск");
        addClientNameTextField.setText("Глеб");
        addClientPositionTextField.setText("");
        addClientPatronymicTextField.setText("Дмитриевич");
        addClientJobTextField.setText("");
        addClientSurnameTextField.setText("Скачков");
        addClientCityTextField.setText("Минск");
        addClientAddressTextField.setText("Козлова");*/


        translate("Russian");
        if (connServer.exists())
            initDataFromServer();


        new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (!connServer.exists())
                        loginBegin();
                    try {
                        sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


        loadLastConfig();
        loginBegin();
    } //INITIALIZE

    @Override
    public void start(Stage primaryStage) throws Exception {
        start = Instant.now();
        System.out.println("Starting...");
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/MainWindow.fxml"));
        primaryStage.setTitle("Main");
        Scene scene = new Scene(root, 800, 500, Color.TRANSPARENT);
        primaryStage.getIcons().add(new Image("assets/client-icon.png"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        root.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });
        root.setOnMouseDragged(mouseEvent -> {
            primaryStage.setX(mouseEvent.getScreenX() - xOffset);
            primaryStage.setY(mouseEvent.getScreenY() - yOffset);
        });
        primaryStage.setMaximized(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        stop = Instant.now();
        System.out.println(String.valueOf(Duration.between(start, stop)));
    }

    @Override
    public void stop() {
        System.out.println("Closing...");
    }

    private void loadLastConfig() {
        try {
            File lastConfig = new File("src/main/java/sample/Controls/lastConfig.txt");
            BufferedReader reader = new BufferedReader(new FileReader(lastConfig));
            String language = reader.readLine();
            String theme = reader.readLine();
            System.out.println("SAVED THEME: " + theme);
            System.out.println("SAVED LANGUAGE: " + language);
            translate("Russian");
            setTheme(theme);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveLastConfig() {
        try {
            if (currentUser != null) {
                File lastConfig = new File("src/main/java/sample/Controls/lastConfig.txt");
                FileWriter lastConfigWriter = new FileWriter(lastConfig, false);
                if (!lastConfig.exists())
                    if (lastConfig.createNewFile())
                        System.out.println("lastConfig.txt created.");

                lastConfigWriter.write(currentUser.getLanguage() + "\n" + currentUser.getTheme());
                lastConfigWriter.flush();
                System.out.println("last config saved");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void translate(String language) throws SQLException {
        switch (language) {
            case "English":
                currentLanguage = "English";
                searchField.setPromptText("Search...");
                languageButton.setText("English");
                loginUsernameLabel.setText("Username");
                loginPasswordLabel.setText("Password");
                loginButton.setText("Log In");
                menuAdminUsersButton.setText(" 1 User management");
                menuAdminSettingsButton.setText(" 3 Settings");
                menuUserSettingsButton.setText(" 3 Settings");
                menuAdminInfoButton.setText(" 4 Information");
                menuUserInfoButton.setText(" 4 Information");
                logoutButtonAdmin.setText(" 5 Log Out");
                logoutButtonUser.setText(" 5 Log Out");


                menuPane1_DBLabel.setText("Connection");
                searchButton.setText("Search");
                //searchButtonClient.setText("Search");
                //criteriaButtonClient.setText("ID");

                languageLabel.setText("Language");
                languageItem_Russian.setText("Russian");
                languageItem_English.setText("English");
                themeLabel.setText("Theme");
                customizationLabel.setText("Customization");
                accountSettingsLabel.setText("Account");
                databaseSettingsLabel.setText("Database connection");
                accountSettingsSaveButton.setText("Save");


                createUser_AnchorPane_Username.setPromptText("Username");
                if (createUser_AnchorPane_AccessMode_MenuButton.getText().equals("Пользователь")
                        || createUser_AnchorPane_AccessMode_MenuButton.getText().equals("User"))
                    createUser_AnchorPane_AccessMode_MenuButton.setText("User");
                if (createUser_AnchorPane_AccessMode_MenuButton.getText().equals("Администратор")
                        || createUser_AnchorPane_AccessMode_MenuButton.getText().equals("Admin"))
                    createUser_AnchorPane_AccessMode_MenuButton.setText("Admin");
                createUser_AccessMenuItem_User.setText("User");
                createUser_AccessMenuItem_Admin.setText("Admin");
                createUser_AnchorPane_Password.setPromptText("Password");
                createUser_AnchorPane_Email.setPromptText("E-Mail");
                createUserButton.setText("Add");

                changeUser_AnchorPane_Username.setPromptText("Username");
                changeUser_AnchorPane_Password.setPromptText("Password");
                changeUser_AnchorPane_Email.setPromptText("E-Mail");
                changeUser_AnchorPane_AccessMode_MenuButton.setText("User");
                changeUser_AccessMenuItem_User.setText("User");
                changeUser_AccessMenuItem_Admin.setText("Admin");
                changeUserButton.setText("Save");

                deleteUserLabel.setText("ID's to delete:");
                deleteUserButton.setText("Delete");

                break;
            case "Russian":
                currentLanguage = "Russian";
                searchField.setPromptText("Искать...");
                //searchFieldClient.setPromptText("Искать...");
                languageButton.setText("Русский");
                loginUsernameLabel.setText("Имя");
                loginPasswordLabel.setText("Пароль");
                loginButton.setText("Войти");
                menuAdminUsersButton.setText(" 1 Управление пользователями");
                menuAdminSettingsButton.setText(" 8 Аккаунт");
                menuUserSettingsButton.setText(" 6 Аккаунт");
                menuAdminInfoButton.setText(" 9 Информация");
                menuUserInfoButton.setText(" 7 Информация");
                logoutButtonAdmin.setText(" 10 Выйти");
                logoutButtonUser.setText(" 8 Выйти");

                menuPane1_DBLabel.setText("Соединение");
                searchButton.setText("Поиск");
                //searchButtonClient.setText("Поиск");
                //criteriaButtonClient.setText("ID");
                languageLabel.setText("Язык");
                languageItem_Russian.setText("Русский");
                languageItem_English.setText("Английский");
                themeLabel.setText("Тема");
                customizationLabel.setText("Кастомизация");
                accountSettingsLabel.setText("Аккаунт");
                databaseSettingsLabel.setText("Соединение с сервером");
                accountSettingsSaveButton.setText("Сохранить");

                createUser_AnchorPane_Username.setPromptText("Имя пользователя");
                if (createUser_AnchorPane_AccessMode_MenuButton.getText().equals("Пользователь")
                        || createUser_AnchorPane_AccessMode_MenuButton.getText().equals("User"))
                    createUser_AnchorPane_AccessMode_MenuButton.setText("Пользователь");
                if (createUser_AnchorPane_AccessMode_MenuButton.getText().equals("Администратор")
                        || createUser_AnchorPane_AccessMode_MenuButton.getText().equals("Admin"))
                    createUser_AnchorPane_AccessMode_MenuButton.setText("Администратор");
                createUser_AccessMenuItem_User.setText("Пользователь");
                createUser_AccessMenuItem_Admin.setText("Администратор");
                createUser_AnchorPane_Password.setPromptText("Пароль");
                createUser_AnchorPane_Email.setPromptText("Электронный адрес");
                createUserButton.setText("Добавить");
                databaseSettingsURLLabel.setText("IP");
                serverConnectButton.setText("Подключиться");

                changeUser_AnchorPane_Username.setPromptText("Имя пользователя");
                changeUser_AnchorPane_Password.setPromptText("Пароль");
                changeUser_AnchorPane_Email.setPromptText("Электронный адрес");
                changeUser_AnchorPane_AccessMode_MenuButton.setText("Пользователь");
                changeUser_AccessMenuItem_User.setText("Пользователь");
                changeUser_AccessMenuItem_Admin.setText("Администратор");
                changeUserButton.setText("Сохранить");

                deleteUserLabel.setText("Удаляемые ID:");
                deleteUserButton.setText("Удалить");

                /*nameColumn.setText("Имя");
                surnameColumn.setText("Фамииля");
                patronymicColumn.setText("Отчество");
                birthDateColumn.setText("Дата рождения");
                birthPlaceColumn.setText("Место рождения");
                passportSeriesColumn.setText("Серия паспорта");
                passportNumberColumn.setText("Номер паспорта");
                issuedByColumn.setText("Орган выдачи");
                issuedDateColumn.setText("Дата выдачи");
                actualResidenceCityColumn.setText("Город проживания");
                actualResidenceAddressColumn.setText("Адрес проживания");
                homeNumberColumn.setText("Домашний телефон");
                mobileNumberColumn.setText("Мобильный телефон");
                emailClientColumn.setText("E-Mail");
                jobColumn.setText("Место работы");
                positionColumn.setText("Должность");
                registrationCityColumn.setText("Город прописки");
                maritalStatusColumn.setText("Семейное положение");
                citizenshipColumn.setText("Гражданство");
                disabilityColumn.setText("Инвалидность");
                retireeColumn.setText("Пенсионер");
                monthlyIncomeColumn.setText("Месячный доход");
                idNumberColumn.setText("Идент. номер");*/

                /*criteriaClientName.setText("Имя");
                criteriaClientSurname.setText("Фамииля");
                criteriaClientPatronymic.setText("Отчество");
                criteriaClientFIO.setText("ФИО");
                criteriaClientBirthDate.setText("Дата рождения");
                criteriaClientBirthPlace.setText("Место рождения");
                criteriaClientPassportSeries.setText("Серия паспорта");
                criteriaClientPassportNumber.setText("Номер паспорта");
                criteriaClientIssuedBy.setText("Орган выдачи");
                criteriaClientIssuedDate.setText("Дата выдачи");
                criteriaClientActCity.setText("Город проживания");
                criteriaClientActAddress.setText("Адрес проживания");
                criteriaClientHomePhone.setText("Домашний телефон");
                criteriaClientMobilePhone.setText("Мобильный телефон");
                criteriaClientEmail.setText("E-Mail");
                criteriaClientJob.setText("Место работы");
                criteriaClientPosition.setText("Должность");
                criteriaClientRegCity.setText("Город прописки");
                criteriaClientMaritalStatus.setText("Семейное положение");
                criteriaClientCitizenship.setText("Гражданство");
                criteriaClientDisability.setText("Инвалидность");
                criteriaClientRetiree.setText("Пенсионер");
                criteriaClientMonthlyIncome.setText("Месячный доход");
                criteriaClientIDNumber.setText("Идент. номер");

                criteriaClientMenuFIO.setText("ФИО");
                criteriaClientMenuPassport.setText("Паспортные данные");
                criteriaClientMenuResidence.setText("Проживание");
                criteriaClientMenuJob.setText("Работа");
                criteriaClientMenuContacts.setText("Контакты");
                criteriaClientMenuOther.setText("Другое");

                addClientNameLabel.setText("Имя*");
                addClientSurnameLabel.setText("Фамилия*");
                addClientPatronymicLabel.setText("Отчество*");
                addClientJobLabel.setText("Работа");
                addClientPositionLabel.setText("Должность");
                addClientRegistrationCityLabel.setText("Город прописки*");
                addClientCityLabel.setText("Город*");
                addClientAddressLabel.setText("Адрес*");
                addClientBirthDateLabel.setText("Дата рождения*");
                addClientBirthPlaceLabel.setText("Место рождения*");
                addClientPassportSeriesLabel.setText("Серия паспорта*");
                addClientPassportNumberLabel.setText("Номер паспорта*");
                addClientIssuedByLabel.setText("Орган выдачи*");
                addClientIssuedDateLabel.setText("Дата выдачи*");
                addClientCitizenshipLabel.setText("Гражданство*");
                addClientIDNumberLabel.setText("Идент. номер*");
                addClientMaritalStatusLabel.setText("Сем. положение*");
                addClientDisabilityLabel.setText("Инвалидность*");
                addClientRetireeLabel.setText("Пенсионер*");
                addClientHomePhoneLabel.setText("Дом. телефон");
                addClientMonthlyIncomeLabel.setText("Месячный доход");
                addClientMobilePhoneLabel.setText("Моб. телефон");
                addClientEmailLabel.setText("E-Mail");

                addClientNameDescription.setText("");
                addClientSurnameDescription.setText("");
                addClientPatronymicDescription.setText("");
                addClientJobDescription.setText("");
                addClientPositionDescription.setText("");
                addClientRegistrationCityDescription.setText("");
                addClientCityDescription.setText("");
                addClientAddressDescription.setText("");
                addClientBirthDateDescription.setText("");
                addClientBirthPlaceDescription.setText("");
                addClientPassportSeriesDescription.setText("");
                addClientPassportNumberDescription.setText("");
                addClientIssuedByDescription.setText("");
                addClientIssuedDateDescription.setText("");
                addClientCitizenshipDescription.setText("");
                addClientIDNumberDescription.setText("");
                addClientMaritalStatusDescription.setText("");
                addClientDisabilityDescription.setText("");
                addClientRetireeDescription.setText("");
                addClientHomePhoneDescription.setText("");
                addClientMonthlyIncomeDescription.setText("");
                addClientMobilePhoneDescription.setText("");
                addClientEmailDescription.setText("");

                addClientMaritalStatusMenuButton.setText("Не в браке");
                addClientMaritalStatusMenuItem_Single.setText("Не в браке");
                addClientMaritalStatusMenuItem_Married.setText("Женат/За мужем");
                addClientMaritalStatusMenuItem_Divorced.setText("Разведён/разведена");
                addClientDisabilityMenuButton.setText("Нет");
                addClientDisabilityMenuItem_FirstGroup.setText("Первая группа");
                addClientDisabilityMenuItem_SecondGroup.setText("Вторая группа");
                addClientDisabilityMenuItem_ThirdGroup.setText("Третья группа");
                addClientDisabilityMenuItem_No.setText("Нет");
                addClientRetireeMenuButton.setText("Нет");
                addClientRetireeMenuItem_Yes.setText("Да");
                addClientRetireeMenuItem_No.setText("Нет");

                addClientLabel.setText("Добавление клиента");
                addClientButton.setText("Добавить");
*/
                break;
        }
        //clientsTable.refresh();
        //initClientsDataServer();
    }

    private void setTheme(String theme) {
        theme = theme.trim();
        theme = theme.toLowerCase();
        switch (theme) {
            case "dark":
                themeButton.setText("Dark");
                currentTheme = "Dark";
                primaryAnchorPane.getStylesheets().clear();
                primaryAnchorPane.getStylesheets().add("CSS/DarkTheme.css");

                break;
            case "light":
                themeButton.setText("Light");
                currentTheme = "Light";
                primaryAnchorPane.getStylesheets().clear();
                primaryAnchorPane.getStylesheets().add("CSS/DarkTheme.css");

                break;
        }
    }

    private void selectMenuItem(Button menuItem, AnchorPane pane) {
        menuAdminUsersButton.setStyle("");
        menuAdminLessonsButton.setStyle("");
        menuAdminTeachersSubjectsButton.setStyle("");
        menuAdminGroupsButton.setStyle("");
        menuAdminStudentsButton.setStyle("");
        menuAdminTeachersButton.setStyle("");
        menuAdminSubjectsButton.setStyle("");
        menuAdminSettingsButton.setStyle("");
        menuAdminInfoButton.setStyle("");

        menuUserWriteInButton.setStyle("");
        menuUserSubjectsButton.setStyle("");
        menuUserTimeTableButton.setStyle("");
        menuUserNotificationsButton.setStyle("");
        menuUserGraphsButton.setStyle("");
        menuUserSettingsButton.setStyle("");
        menuUserInfoButton.setStyle("");

        setAllPanesInvisible();
        if (pane == menuPaneLessons) {
            menuPaneLessons.setVisible(true);
            lessonsScrollPane.setVisible(true);
            lessonsAnchorPane.requestFocus();
        } else if (pane == menuPaneTeachersSubjects) {
            menuPaneTeachersSubjects.setVisible(true);
            teachersSubjectsScrollPane.setVisible(true);
            teachersSubjectsAnchorPane.requestFocus();
        } else if (pane == menuPaneGroups) {
            menuPaneGroups.setVisible(true);
            groupsScrollPane.setVisible(true);
            groupsAnchorPane.requestFocus();
        } else if (pane == menuPaneStudents) {
            menuPaneStudents.setVisible(true);
            studentsScrollPane.setVisible(true);
            studentsAnchorPane.requestFocus();
        } else if (pane == menuPaneTeachers) {
            menuPaneTeachers.setVisible(true);
            teachersScrollPane.setVisible(true);
            teachersAnchorPane.requestFocus();
        } else if (pane == menuPaneSubjects) {
            menuPaneSubjects.setVisible(true);
            subjectsScrollPane.setVisible(true);
            subjectsAnchorPane.requestFocus();
        } else {
            pane.setVisible(true);
            pane.requestFocus();
        }
        if (currentUser.getTheme().equals("Dark"))
            menuItem.setStyle("-fx-border-width: 1 1 1 4;" +
                    "-fx-border-color: #D8D8D8");
    }

    private synchronized void loginBegin() {
        try {
            menuAdmin.setVisible(false);
            menuUser.setVisible(false);
            leftAnchorPane.setDisable(true);
            if (currentUser != null) connServer.sendString("setCurrentUser|null|");
            currentUser = null;
            setAllPanesInvisible();
            loginPane.setVisible(true);
        } catch (IllegalStateException e) {
            System.out.println("GG");
        }
    }

    private void loginSuccess() throws SQLException {
        leftAnchorPane.setDisable(false);
        if (currentUser.getAccessMode() == 1) {
            menuAdmin.setVisible(true);
            menuAdminInfoButton.fire();
            databaseSettingsPane.setDisable(false);
        } else {
            menuUser.setVisible(true);
            menuUserInfoButton.fire();
            databaseSettingsPane.setDisable(true);
        }
        setTheme(currentUser.getTheme());
        currentTheme = currentUser.getTheme();
        currentLanguage = currentUser.getLanguage();
        currentUserLabelAdmin.setText(currentUser.getUsername());
        currentUserLabelUser.setText(currentUser.getUsername());
        accountSettingsUsernameTextField.setText(currentUser.getUsername());
        accountSettingsPasswordTextField.setText(currentUser.getPassword());
        accountSettingsEmailTextField.setText(currentUser.getEmail());

        connServer.sendString("setCurrentUser|" + currentUser.getUsername() + "|");
        loginPane.setVisible(false);
        loginWarning.setText("");
    }

    private void setAllPanesInvisible() {
        menuPaneUsers.setVisible(false);
        menuPaneLessons.setVisible(false);
        menuPaneTeachersSubjects.setVisible(false);
        menuPaneGroups.setVisible(false);
        menuPaneStudents.setVisible(false);
        menuPaneTeachers.setVisible(false);
        menuPaneSubjects.setVisible(false);
        menuPaneSettings.setVisible(false);
        menuPaneInfo.setVisible(false);
        menuPaneUserWriteIn.setVisible(false);
        menuPaneUserSubjects.setVisible(false);
        menuPaneUserTimeTable.setVisible(false);
        menuPaneUserNotifications.setVisible(false);
        menuPaneUserGraphs.setVisible(false);
        loginPane.setVisible(false);
    }

    private void changeAccountData() {
        boolean was = false;
        String enteredUsername = accountSettingsUsernameTextField.getText();
        String enteredEmail = accountSettingsEmailTextField.getText();
        if (enteredEmail.equals(""))
            enteredEmail = "null";
        String enteredPassword;
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return;
        }
        byte[] hash = digest.digest(accountSettingsPasswordTextField.getText().trim().getBytes(StandardCharsets.UTF_8));
        enteredPassword = Base64.getEncoder().encodeToString(hash);

        settingsWarningLabel.setStyle("-fx-text-fill: #d85751");
        if (!(enteredPassword.length() < 3 || enteredUsername.length() < 3)) {
            if (!connServer.isClosed()) {
                for (User u : usersData)
                    if (enteredUsername.equals(u.getUsername()) && currentUser.getId() != u.getId()) {
                        was = true;
                        break;
                    }

                if (!was) {
                    connServer.sendString("changeAccountData|" + currentUser.getId() + "|" + enteredUsername + "|" + enteredPassword + "|" + enteredEmail + "|");

                    new Thread() {
                        @Override
                        public void run() {
                            initDataFromServer();
                        }
                    }.start();
                    settingsWarningLabel.setStyle("-fx-text-fill: #7f8e55");
                    settingsWarningLabel.setText("Account information saved");
                } else
                    settingsWarningLabel.setText("Username is not free");
            } else
                settingsWarningLabel.setText("No connection");
        } else
            settingsWarningLabel.setText("Username/password must be at least 3 characters");
    }

    private void changeUser() {
        boolean was = false;
        int enteredId =
                StringUtils.isStrictlyNumeric(changeUser_AnchorPane_Id.getText()) ? Integer.parseInt(changeUser_AnchorPane_Id.getText()) : 0;
        String enteredUsername = changeUser_AnchorPane_Username.getText();
        String enteredPassword = changeUser_AnchorPane_Password.getText();

        String enteredEmail = changeUser_AnchorPane_Email.getText();
        int enteredAccessMode = (changeUser_AnchorPane_AccessMode_MenuButton.getText().equals("Admin") ||
                changeUser_AnchorPane_AccessMode_MenuButton.getText().equals("Администратор")) ? 1 : 0;

        if (submitId()) {
            if (!(enteredPassword.length() < 3 && enteredUsername.length() < 3)) {
                if (!connServer.isClosed()) {
                    for (User u : usersData)
                        if (enteredUsername.equals(u.getUsername()))
                            if (enteredId != u.getId()) {
                                was = true;
                                break;
                            }

                    if (!was) {
                        changeUser_AnchorPane_Id.setText("");
                        changeUser_AnchorPane_Username.setText("");
                        changeUser_AnchorPane_Password.setText("");
                        changeUser_AnchorPane_Email.setText("");
                        for (User u : usersData)
                            if (u.getId() == enteredId)
                                if (enteredPassword.matches("[0-9a-zA-Z]{6,}")) {
                                    u.setUsernameServer(connServer, enteredUsername);
                                    if (!enteredPassword.equals("previous"))
                                        u.setPasswordServer(connServer, enteredPassword);
                                    u.setAccessModeServer(connServer, String.valueOf(enteredAccessMode));
                                    u.setEMailServer(connServer, enteredEmail);
                                }
                        new Thread() {
                            @Override
                            public void run() {
                                initDataFromServer();
                            }
                        }.start();
                    }
                }
            }
        } else
            System.out.println("Unknown ID");
    }

    private boolean submitId() {
        if (StringUtils.isStrictlyNumeric(changeUser_AnchorPane_Id.getText())) {
            int id = Integer.parseInt(changeUser_AnchorPane_Id.getText());
            if (!connServer.isClosed())
                for (User u : usersData)
                    if (id == u.getId()) {
                        changeUser_AnchorPane_Username.setText(u.getUsername());
                        changeUser_AnchorPane_Email.setText(u.getEmail());
                        changeUser_AnchorPane_Password.setText("previous");
                        if (currentLanguage.equals("English"))
                            changeUser_AnchorPane_AccessMode_MenuButton.setText((u.getAccessMode() == 0) ? "User" : "Admin");
                        if (currentLanguage.equals("Russian"))
                            changeUser_AnchorPane_AccessMode_MenuButton.setText((u.getAccessMode() == 0) ? "Пользователь" : "Администратор");
                        return true;
                    }
        }
        System.out.println("WRONG ID");
        return false;
    }

    private void deleteUsers() {
        if (deleteUserTextField.getText().equals("all")) {
            deleteUserTextField.setText("");
            connServer.sendString("deleteAllUsers");
            new Thread() {
                @Override
                public void run() {
                    initDataFromServer();
                }
            }.start();
        } else {
            String[] ids = deleteUserTextField.getText().split(",");
            for (String id : ids) {
                if (StringUtils.isStrictlyNumeric(id)) {
                    deleteUserTextField.setText("");
                    for (User u : usersData)
                        if (u.getId() == Integer.parseInt(id))
                            u.deleteServer(connServer);
                }
            }
            new Thread() {
                @Override
                public void run() {
                    initDataFromServer();
                }
            }.start();
        }
    }

    private void registration_Admin(Label warningLabel, String username, String password, String email, String accessMode) {
        int access_mode = accessMode.equals("User") || accessMode.equals("Пользователь") ? 0 : 1;
        if (warningLabel == null)
            warningLabel = new Label();
        warningLabel.setStyle("-fx-text-fill: #d85751");
        boolean was = false;

        if (!(password.length() < 3 || username.length() < 3)) {
            for (User u : usersData)
                if (username.equals(u.getUsername())) {
                    was = true;
                    break;
                }

            if (!was) {
                if (password.matches("[0-9a-zA-Z]{6,}")) {
                    createUser_AnchorPane_Username.setText("");
                    createUser_AnchorPane_Password.setText("");
                    createUser_AnchorPane_Email.setText("");
                    User u = new User(0, access_mode, username, password, email, false);
                    connServer.sendString("addUser|" + u.toString());
                    new Thread() {
                        @Override
                        public void run() {
                            initDataFromServer();
                            System.out.println("added");
                        }
                    }.start();

                    warningLabel.setStyle("-fx-text-fill: #7f8e55");
                    warningLabel.setText(username + " registered");
                } else
                    warningLabel.setText("Incorrect password format");
            } else
                warningLabel.setText("Username is not free");
        } else {
            warningLabel.setText("Username/password must be at least 3 characters");
        }
    }

    private void registration_User(Label warningLabel, String username, String password) {
        if (warningLabel == null)
            warningLabel = new Label();
        warningLabel.setStyle("-fx-text-fill: #d85751");
        boolean was = false;

        if (!(password.length() < 3 || username.length() < 3)) {
            for (User u : usersData)
                if (username.equals(u.getUsername())) {
                    was = true;
                    break;
                }

            if (!was) {
                if (password.matches("[0-9a-zA-Z]{6,}")) {
                    User u = new User(0, 0, username, password, "", false);
                    connServer.sendString("addUser|" + u.toString());
                    new Thread() {
                        @Override
                        public void run() {
                            initDataFromServer();
                            System.out.println("added");
                        }
                    }.start();

                    warningLabel.setStyle("-fx-text-fill: #7f8e55");
                    warningLabel.setText(username + " registered");
                } else
                    warningLabel.setText("Incorrect password format");
            } else
                warningLabel.setText("Username is not free");
        } else
            warningLabel.setText("Username/password must be at least 3 characters");
    }

    /*private void newConnection() {
        String enteredURL = databaseSettingsURLTextField.getText();
        String enteredUsername = databaseSettingsUsernameTextField.getText();
        String enteredPassword = databaseSettingsPasswordTextField.getText();

        databaseSettingsConnectionStatusLabel.setStyle("-fx-text-fill: #d85751");
        if (!(enteredUsername.length() < 3 || enteredPassword.length() < 3))
            switch (connDB.setConnection(enteredURL, enteredUsername, enteredPassword)) {
                case 1:
                    databaseSettingsConnectionStatusLabel.setStyle("-fx-text-fill: #7f8e55");
                    databaseSettingsConnectionStatusLabel.setText("Connected!");
                    break;
                case 0:
                    databaseSettingsConnectionStatusLabel.setText("Wrong URL!");
                    break;
                case 1045:
                    databaseSettingsConnectionStatusLabel.setText("Wrong username/password!");
                    break;
                case 555:
                    databaseSettingsConnectionStatusLabel.setText("???");
                    break;
            }
    }*/

    private void minimize() {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        if (stage.isMaximized() && theme == 0) {
            stage.setMaximized(false);
            usersTable.setPrefHeight(154d);


            lessonsTable.setPrefWidth(513d);
            lessonsTable.setPrefHeight(200d);
            teachersSubjectsTable.setPrefWidth(513d);
            teachersSubjectsTable.setPrefHeight(200d);
            groupsTable.setPrefWidth(513d);
            groupsTable.setPrefHeight(200d);
            studentsTable.setPrefWidth(513d);
            studentsTable.setPrefHeight(200d);
            teachersTable.setPrefWidth(513d);
            teachersTable.setPrefHeight(200d);
            subjectsTable.setPrefWidth(513d);
            subjectsTable.setPrefHeight(200d);


            createUser_AnchorPane.setLayoutY(212);
            minimizeButton.setStyle("-fx-background-image: url(assets/expand-white.png)");
            loginElementsPane.setLayoutX(250);
            loginElementsPane.setLayoutY(176);
            loginWarning.setLayoutX(45);
            loginWarning.setLayoutY(117);
            searchField.setPrefWidth(136);
            AnchorPane.setTopAnchor(createLessonAnchorPane, 263.6);
            AnchorPane.setTopAnchor(createTeacherSubjectAnchorPane, 263.6);
            AnchorPane.setTopAnchor(createGroupAnchorPane, 263.6);
            AnchorPane.setTopAnchor(createStudentAnchorPane, 263.6);
            AnchorPane.setTopAnchor(createTeacherAnchorPane, 263.6);
            AnchorPane.setTopAnchor(createSubjectAnchorPane, 263.6);

            lessonsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            lessonsScrollPane.setPrefWidth(573);
            lessonsAnchorPane.setPrefWidth(572);
            lessonsScrollPane.setPrefHeight(462);
            lessonsAnchorPane.setPrefHeight(459);

            teachersSubjectsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            teachersSubjectsScrollPane.setPrefWidth(573);
            teachersSubjectsAnchorPane.setPrefWidth(572);
            teachersSubjectsScrollPane.setPrefHeight(462);
            teachersSubjectsAnchorPane.setPrefHeight(459);

            groupsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            groupsScrollPane.setPrefWidth(573);
            groupsAnchorPane.setPrefWidth(572);
            groupsScrollPane.setPrefHeight(462);
            groupsAnchorPane.setPrefHeight(459);

            studentsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            studentsScrollPane.setPrefWidth(573);
            studentsAnchorPane.setPrefWidth(572);
            studentsScrollPane.setPrefHeight(462);
            studentsAnchorPane.setPrefHeight(459);

            teachersScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            teachersScrollPane.setPrefWidth(573);
            teachersAnchorPane.setPrefWidth(572);
            teachersScrollPane.setPrefHeight(462);
            teachersAnchorPane.setPrefHeight(459);

            subjectsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            subjectsScrollPane.setPrefWidth(573);
            subjectsAnchorPane.setPrefWidth(572);
            subjectsScrollPane.setPrefHeight(462);
            subjectsAnchorPane.setPrefHeight(459);

            createLessonAnchorPane.setPrefHeight(930);
            createTeacherSubjectAnchorPane.setPrefHeight(930);
            createGroupAnchorPane.setPrefHeight(930);
            createStudentAnchorPane.setPrefHeight(930);
            createTeacherAnchorPane.setPrefHeight(930);
            createSubjectAnchorPane.setPrefHeight(930);
        } else {
            stage.setMaximized(true);
            usersTable.setPrefHeight(606d);
            createUser_AnchorPane.setLayoutY(667);
            minimizeButton.setStyle("-fx-background-image: url(assets/minimize-white.png)");
            loginElementsPane.setLayoutX(610);
            loginElementsPane.setLayoutY(350);
            loginWarning.setLayoutX(405);
            loginWarning.setLayoutY(290);
            searchField.setPrefWidth(350);
            AnchorPane.setTopAnchor(createLessonAnchorPane, 630.0);
            AnchorPane.setTopAnchor(createTeacherSubjectAnchorPane, 630.0);
            AnchorPane.setTopAnchor(createGroupAnchorPane, 630.0);
            AnchorPane.setTopAnchor(createStudentAnchorPane, 630.0);
            AnchorPane.setTopAnchor(createTeacherAnchorPane, 630.0);
            AnchorPane.setTopAnchor(createSubjectAnchorPane, 630.0);

            lessonsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            lessonsScrollPane.setPrefWidth(1310);
            lessonsAnchorPane.setPrefWidth(1304);
            lessonsScrollPane.setPrefHeight(850);
            lessonsAnchorPane.setPrefHeight(820);

            teachersSubjectsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            teachersSubjectsScrollPane.setPrefWidth(1310);
            teachersSubjectsAnchorPane.setPrefWidth(1304);
            teachersSubjectsScrollPane.setPrefHeight(850);
            teachersSubjectsAnchorPane.setPrefHeight(820);

            groupsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            groupsScrollPane.setPrefWidth(1310);
            groupsAnchorPane.setPrefWidth(1304);
            groupsScrollPane.setPrefHeight(850);
            groupsAnchorPane.setPrefHeight(820);

            studentsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            studentsScrollPane.setPrefWidth(1310);
            studentsAnchorPane.setPrefWidth(1304);
            studentsScrollPane.setPrefHeight(850);
            studentsAnchorPane.setPrefHeight(820);

            teachersScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            teachersScrollPane.setPrefWidth(1310);
            teachersAnchorPane.setPrefWidth(1304);
            teachersScrollPane.setPrefHeight(850);
            teachersAnchorPane.setPrefHeight(820);

            subjectsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            subjectsScrollPane.setPrefWidth(1310);
            subjectsAnchorPane.setPrefWidth(1304);
            subjectsScrollPane.setPrefHeight(850);
            subjectsAnchorPane.setPrefHeight(820);

            createLessonAnchorPane.setPrefHeight(380);
            createTeacherSubjectAnchorPane.setPrefHeight(380);
            createGroupAnchorPane.setPrefHeight(380);
            createStudentAnchorPane.setPrefHeight(380);
            createTeacherAnchorPane.setPrefHeight(380);
            createSubjectAnchorPane.setPrefHeight(380);
        }
    }

    /*private void initUsersData() throws SQLException {
        if (!connServer.isClosed()) {
            connectionIndicator.setStyle("-fx-background-image: url(assets/indicator-green.png)");
            usersTable.setItems(usersData);
            Statement statement = connDB.getConnection().createStatement();
            Statement statement2 = connDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  * FROM users");
            usersData.clear();

            System.out.println();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setAccessMode(resultSet.getInt("access_mode"));
                user.setUsername(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setEMail(resultSet.getString("email"));
                ResultSet resultSetConfigs = statement2.executeQuery("SELECT * FROM user_configs");
                while (resultSetConfigs.next())
                    if (resultSetConfigs.getInt("userId") == user.getId()) {
                        user.setTheme(resultSetConfigs.getString("theme"));
                        user.setLanguage(resultSetConfigs.getString("language"));
                    }
                usersData.add(user);
                System.out.println(user);
            }
        } else
            connectionIndicator.setStyle("-fx-background-image: url(assets/indicator-red.png)");
    }

    private void initClientsData() throws SQLException {
        if (!connServer.isClosed()) {
            connectionIndicator.setStyle("-fx-background-image: url(assets/indicator-green.png)");
            Statement statement = connDB.getConnection().createStatement();
            Statement statement2 = connDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM clients");
            clientsTable.setItems(clientsData);
            clientsData.clear();

            System.out.println();
            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt("Id"));
                client.setName(resultSet.getString("Name"));
                client.setSurname(resultSet.getString("Surname"));
                client.setPatronymic(resultSet.getString("Patronymic"));
                client.setBirthDate(resultSet.getDate("Birth_date").toString());
                client.setPassportSeries(resultSet.getString("Passport_series"));
                client.setPassportNumber(resultSet.getString("Passport_number"));
                client.setIssuedBy(resultSet.getString("Issued_by"));
                client.setIssuedDate(resultSet.getDate("Issued_date").toString());
                client.setBirthPlace(resultSet.getString("Birth_place"));
                client.setActualResidenceCity(resultSet.getString("Actual_residence_city"));
                client.setActualResidenceAddress(resultSet.getString("Actual_residence_address"));
                client.setHomeNumber(resultSet.getString("Home_number"));
                client.setMobileNumber(resultSet.getString("Mobile_number"));
                client.setEmail(resultSet.getString("Email"));
                client.setJob(resultSet.getString("Job"));
                client.setPosition(resultSet.getString("Position"));
                client.setRegistrationCity(resultSet.getString("Registration_city"));
                client.setMaritalStatus(MaritalStatus.valueOf(resultSet.getString("Marital_status")));
                client.setCitizenship(resultSet.getString("Citizenship"));
                client.setDisability(Disability.valueOf(resultSet.getString("Disability")));
                client.setRetiree(Retiree.valueOf(resultSet.getString("Is_retiree")));
                client.setMonthlyIncome(resultSet.getString("Monthly_income"));
                client.setIdNumber(resultSet.getString("Id_number"));
                clientsData.add(client);
                System.out.println(client);
            }
        } else
            connectionIndicator.setStyle("-fx-background-image: url(assets/indicator-red.png)");

    }
*/
    private synchronized void initUsersDataServerBuffer() {
        usersTable.setItems(usersData);
        usersData.clear();
        for (User u : connServer.getUserList())
            usersData.add(u);
        usersTable.refresh();
    }

    private synchronized void initGroupsDataServerBuffer() {
        groupsTable.setItems(groupsData);
        groupsData.clear();
        for (Group g : connServer.getGroupsList())
            groupsData.add(g);
        groupsTable.refresh();
    }

    private synchronized void initLessonsDataServerBuffer() {
        lessonsTable.setItems(lessonsData);
        lessonsData.clear();
        for (Lesson l : connServer.getLessonsList())
            lessonsData.add(l);
        lessonsTable.refresh();
    }

    private synchronized void initStudentsDataServerBuffer() {
        studentsTable.setItems(studentsData);
        studentsData.clear();
        for (Student st : connServer.getStudentsList())
            studentsData.add(st);
        studentsTable.refresh();
    }

    private synchronized void initSubjectsDataServerBuffer() {
        subjectsTable.setItems(subjectsData);
        subjectsData.clear();
        for (Subject sb : connServer.getSubjectsList())
            subjectsData.add(sb);
        subjectsTable.refresh();
    }

    private synchronized void initTeachersDataServerBuffer() {
        teachersTable.setItems(teachersData);
        teachersData.clear();
        for (Teacher t : connServer.getTeachersList())
            teachersData.add(t);
        teachersTable.refresh();
    }

    private synchronized void initTeachersSubjectsDataServerBuffer() {
        teachersSubjectsTable.setItems(teachersSubjectsData);
        teachersSubjectsData.clear();
        for (TeacherSubject ts : connServer.getTeachersSubjectsList())
            teachersSubjectsData.add(ts);
        teachersSubjectsTable.refresh();
    }


    private void addButtonsToTable() {
        maritalStatusColumn = new TableColumn("Marital Status");
        disabilityColumn = new TableColumn("Disability");
        retireeColumn = new TableColumn("Retiree");
        deleteLessonColumn = new TableColumn("");
        deleteTeacherSubjectColumn = new TableColumn("");
        deleteGroupColumn = new TableColumn("");
        deleteStudentColumn = new TableColumn("");
        deleteTeacherColumn = new TableColumn("");
        deleteSubjectColumn = new TableColumn("");

        maritalStatusColumn.setMinWidth(180);
        disabilityColumn.setMinWidth(180);
        retireeColumn.setMinWidth(80);
        deleteLessonColumn.setMaxWidth(23);
        deleteLessonColumn.setMinWidth(23);
        deleteTeacherSubjectColumn.setMaxWidth(23);
        deleteTeacherSubjectColumn.setMinWidth(23);
        deleteGroupColumn.setMaxWidth(23);
        deleteGroupColumn.setMinWidth(23);
        deleteStudentColumn.setMaxWidth(23);
        deleteStudentColumn.setMinWidth(23);
        deleteTeacherColumn.setMaxWidth(23);
        deleteTeacherColumn.setMinWidth(23);
        deleteSubjectColumn.setMaxWidth(23);
        deleteSubjectColumn.setMinWidth(23);

        maritalStatusColumn.setResizable(false);
        disabilityColumn.setResizable(false);
        retireeColumn.setResizable(false);
        deleteLessonColumn.setResizable(false);
        deleteTeacherSubjectColumn.setResizable(false);
        deleteGroupColumn.setResizable(false);
        deleteStudentColumn.setResizable(false);
        deleteTeacherColumn.setResizable(false);
        deleteSubjectColumn.setResizable(false);

        Callback<TableColumn<Client, Void>, TableCell<Client, Void>> cellFactory1 = new Callback<>() {
            @Override
            public TableCell<Client, Void> call(TableColumn<Client, Void> param) {

                return new TableCell<>() {
                    MenuItem mi1 = new MenuItem("Single");
                    MenuItem mi2 = new MenuItem("Married");
                    MenuItem mi3 = new MenuItem("Divorced");
                    MenuItem mi4 = new MenuItem("Unknown");

                    private MenuButton btn =
                            new MenuButton("Unknown", null, mi1, mi2, mi3, mi4);

                    {
                        btn.setMinWidth(170);
                        mi1.setOnAction(actionEvent -> {
                            Client data = getTableView().getItems().get(getIndex());
                            data.setMaritalStatusServer(connServer, MaritalStatus.Single);
                            btn.setText(mi1.getText());
                        });
                        mi2.setOnAction(actionEvent -> {
                            Client data = getTableView().getItems().get(getIndex());
                            data.setMaritalStatusServer(connServer, MaritalStatus.Married);
                            btn.setText(mi2.getText());
                        });
                        mi3.setOnAction(actionEvent -> {
                            Client data = getTableView().getItems().get(getIndex());
                            data.setMaritalStatusServer(connServer, MaritalStatus.Divorced);
                            btn.setText(mi3.getText());
                        });
                        mi4.setOnAction(actionEvent -> {
                            Client data = getTableView().getItems().get(getIndex());
                            data.setMaritalStatusServer(connServer, MaritalStatus.Unknown);
                            btn.setText(mi4.getText());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            String toSet;
                            Client data = getTableView().getItems().get(getIndex());
                            if (currentLanguage.equals("English")) {
                                switch (data.getMaritalStatus()) {
                                    case "Single":
                                        toSet = "Single";
                                        break;
                                    case "Married":
                                        toSet = "Married";
                                        break;
                                    case "Divorced":
                                        toSet = "Divorced";
                                        break;
                                    case "Unknown":
                                        toSet = "Unknown";
                                        break;
                                    default:
                                        toSet = "Error";
                                }
                                btn.setText(toSet);
                                mi1.setText("Single");
                                mi2.setText("Married");
                                mi3.setText("Divorced");
                                mi4.setText("Unknown");
                            }
                            if (currentLanguage.equals("Russian")) {
                                switch (data.getMaritalStatus()) {
                                    case "Single":
                                        toSet = "Не в браке";
                                        break;
                                    case "Married":
                                        toSet = "Женат/За мужем";
                                        break;
                                    case "Divorced":
                                        toSet = "Разведён/разведена";
                                        break;
                                    case "Unknown":
                                        toSet = "Не указано";
                                        break;
                                    default:
                                        toSet = "Error";
                                }
                                btn.setText(toSet);
                                mi1.setText("Не в браке");
                                mi2.setText("Женат/За мужем");
                                mi3.setText("Разведён/разведена");
                                mi4.setText("Не указано");
                            }
                            setGraphic(btn);
                        }
                    }
                };
            }
        };
        Callback<TableColumn<Client, Void>, TableCell<Client, Void>> cellFactory2 = new Callback<>() {
            @Override
            public TableCell<Client, Void> call(TableColumn<Client, Void> param) {
                return new TableCell<>() {
                    MenuItem mi1 = new MenuItem("First group");
                    MenuItem mi2 = new MenuItem("Second group");
                    MenuItem mi3 = new MenuItem("Third group");
                    MenuItem mi4 = new MenuItem("No");
                    MenuItem mi5 = new MenuItem("Unknown");

                    private MenuButton btn =
                            new MenuButton("No", null, mi1, mi2, mi3, mi4);

                    {
                        btn.setMinWidth(170);
                        mi1.setOnAction(actionEvent -> {
                            Client data = getTableView().getItems().get(getIndex());
                            data.setDisabilityServer(connServer, Disability.First_group);
                            btn.setText(mi1.getText());
                        });
                        mi2.setOnAction(actionEvent -> {
                            Client data = getTableView().getItems().get(getIndex());
                            data.setDisabilityServer(connServer, Disability.Second_group);
                            btn.setText(mi2.getText());
                        });
                        mi3.setOnAction(actionEvent -> {
                            Client data = getTableView().getItems().get(getIndex());
                            data.setDisabilityServer(connServer, Disability.Third_group);
                            btn.setText(mi3.getText());
                        });
                        mi4.setOnAction(actionEvent -> {
                            Client data = getTableView().getItems().get(getIndex());
                            data.setDisabilityServer(connServer, Disability.No);
                            btn.setText(mi4.getText());
                        });
                        mi5.setOnAction(actionEvent -> {
                            Client data = getTableView().getItems().get(getIndex());
                            data.setDisabilityServer(connServer, Disability.Unknown);
                            btn.setText(mi4.getText());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            String toSet;
                            Client data = getTableView().getItems().get(getIndex());
                            if (currentLanguage.equals("English")) {
                                switch (data.getDisability()) {
                                    case "First_group":
                                        toSet = "First group";
                                        break;
                                    case "Second_group":
                                        toSet = "Second group";
                                        break;
                                    case "Third_group":
                                        toSet = "Third group";
                                        break;
                                    case "No":
                                        toSet = "No";
                                        break;
                                    case "Unknown":
                                        toSet = "Unknown";
                                        break;
                                    default:
                                        toSet = "Error";
                                }
                                btn.setText(toSet);
                                mi1.setText("First group");
                                mi2.setText("Second group");
                                mi3.setText("Third group");
                                mi4.setText("No");
                                mi5.setText("Unknown");
                            }
                            if (currentLanguage.equals("Russian")) {
                                switch (data.getDisability()) {
                                    case "First_group":
                                        toSet = "Первая группа";
                                        break;
                                    case "Second_group":
                                        toSet = "Вторая группа";
                                        break;
                                    case "Third_group":
                                        toSet = "Третья группа";
                                        break;
                                    case "No":
                                        toSet = "Нет";
                                        break;
                                    case "Unknown":
                                        toSet = "Не указано";
                                        break;
                                    default:
                                        toSet = "Error";
                                }
                                btn.setText(toSet);
                                mi1.setText("Первая группа");
                                mi2.setText("Вторая группа");
                                mi3.setText("Третья группа");
                                mi4.setText("Нет");
                                mi5.setText("Не указано");
                            }
                            setGraphic(btn);
                        }
                    }
                };
            }
        };
        Callback<TableColumn<Client, Void>, TableCell<Client, Void>> cellFactory3 = new Callback<>() {
            @Override
            public TableCell<Client, Void> call(TableColumn<Client, Void> param) {

                return new TableCell<>() {
                    MenuItem mi1 = new MenuItem("Yes");
                    MenuItem mi2 = new MenuItem("No");

                    private MenuButton btn =
                            new MenuButton("No", null, mi1, mi2);

                    {
                        btn.setMinWidth(70);
                        mi1.setOnAction(actionEvent -> {
                            Client data = getTableView().getItems().get(getIndex());
                            data.setRetireeServer(connServer, Retiree.Yes);
                            btn.setText(mi1.getText());
                        });
                        mi2.setOnAction(actionEvent -> {
                            Client data = getTableView().getItems().get(getIndex());
                            data.setRetireeServer(connServer, Retiree.No);
                            btn.setText(mi2.getText());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            String toSet;
                            Client data = getTableView().getItems().get(getIndex());
                            if (currentLanguage.equals("English")) {
                                switch (data.getRetiree()) {
                                    case "Yes":
                                        toSet = "Yes";
                                        break;
                                    case "No":
                                        toSet = "No";
                                        break;
                                    default:
                                        toSet = "Error";
                                }
                                btn.setText(toSet);
                                mi1.setText("Yes");
                                mi2.setText("No");
                            }
                            if (currentLanguage.equals("Russian")) {
                                switch (data.getRetiree()) {
                                    case "Yes":
                                        toSet = "Да";
                                        break;
                                    case "No":
                                        toSet = "Нет";
                                        break;
                                    default:
                                        toSet = "Error";
                                }
                                btn.setText(toSet);
                                mi1.setText("Да");
                                mi2.setText("Нет");
                            }
                            setGraphic(btn);
                        }
                    }
                };
            }
        };
        Callback<TableColumn<Lesson, Void>, TableCell<Lesson, Void>> cellFactoryDeleteLesson = new Callback<>() {
            @Override
            public TableCell<Lesson, Void> call(TableColumn<Lesson, Void> param) {
                return new TableCell<>() {

                    private Button btn =
                            new Button("");

                    {
                        btn.getStyleClass().add("deleteClientButton");
                        btn.setMinWidth(15);
                        btn.setPrefWidth(15);
                        btn.setOnAction(event -> {
                            getTableView().getItems().get(getIndex()).deleteServer(connServer);
                            lessonsData.remove(getTableView().getItems().get(getIndex()));
                            //clientsTable.refresh();
                            new Thread() {
                                @Override
                                public void run() {
                                    initDataFromServer();
                                    System.out.println("deleted");
                                }
                            }.start();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            String toSet;
                            if (currentLanguage.equals("English")) {
                                btn.setText("");
                            }
                            if (currentLanguage.equals("Russian")) {
                                btn.setText("");
                            }
                            setGraphic(btn);
                        }
                    }
                };
            }
        };
        Callback<TableColumn<TeacherSubject, Void>, TableCell<TeacherSubject, Void>> cellFactoryDeleteTeacherSubject =
                new Callback<>() {
                    @Override
                    public TableCell<TeacherSubject, Void> call(TableColumn<TeacherSubject, Void> param) {
                        return new TableCell<>() {

                            private Button btn =
                                    new Button("");

                            {
                                btn.getStyleClass().add("deleteClientButton");
                                btn.setMinWidth(15);
                                btn.setPrefWidth(15);
                                btn.setOnAction(event -> {
                                    getTableView().getItems().get(getIndex()).deleteServer(connServer);
                                    lessonsData.remove(getTableView().getItems().get(getIndex()));
                                    //clientsTable.refresh();
                                    new Thread() {
                                        @Override
                                        public void run() {
                                            initDataFromServer();
                                            System.out.println("deleted");
                                        }
                                    }.start();
                                });
                            }

                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    String toSet;
                                    if (currentLanguage.equals("English")) {
                                        btn.setText("");
                                    }
                                    if (currentLanguage.equals("Russian")) {
                                        btn.setText("");
                                    }
                                    setGraphic(btn);
                                }
                            }
                        };
                    }
                };
        Callback<TableColumn<Group, Void>, TableCell<Group, Void>> cellFactoryDeleteGroup = new Callback<>() {
            @Override
            public TableCell<Group, Void> call(TableColumn<Group, Void> param) {
                return new TableCell<>() {

                    private Button btn =
                            new Button("");

                    {
                        btn.getStyleClass().add("deleteClientButton");
                        btn.setMinWidth(15);
                        btn.setPrefWidth(15);
                        btn.setOnAction(event -> {
                            getTableView().getItems().get(getIndex()).deleteServer(connServer);
                            lessonsData.remove(getTableView().getItems().get(getIndex()));
                            //clientsTable.refresh();
                            new Thread() {
                                @Override
                                public void run() {
                                    initDataFromServer();
                                    System.out.println("deleted");
                                }
                            }.start();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            String toSet;
                            if (currentLanguage.equals("English")) {
                                btn.setText("");
                            }
                            if (currentLanguage.equals("Russian")) {
                                btn.setText("");
                            }
                            setGraphic(btn);
                        }
                    }
                };
            }
        };
        Callback<TableColumn<Student, Void>, TableCell<Student, Void>> cellFactoryDeleteStudent = new Callback<>() {
            @Override
            public TableCell<Student, Void> call(TableColumn<Student, Void> param) {
                return new TableCell<>() {

                    private Button btn =
                            new Button("");

                    {
                        btn.getStyleClass().add("deleteClientButton");
                        btn.setMinWidth(15);
                        btn.setPrefWidth(15);
                        btn.setOnAction(event -> {
                            getTableView().getItems().get(getIndex()).deleteServer(connServer);
                            lessonsData.remove(getTableView().getItems().get(getIndex()));
                            //clientsTable.refresh();
                            new Thread() {
                                @Override
                                public void run() {
                                    initDataFromServer();
                                    System.out.println("deleted");
                                }
                            }.start();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            String toSet;
                            if (currentLanguage.equals("English")) {
                                btn.setText("");
                            }
                            if (currentLanguage.equals("Russian")) {
                                btn.setText("");
                            }
                            setGraphic(btn);
                        }
                    }
                };
            }
        };
        Callback<TableColumn<Teacher, Void>, TableCell<Teacher, Void>> cellFactoryDeleteTeacher = new Callback<>() {
            @Override
            public TableCell<Teacher, Void> call(TableColumn<Teacher, Void> param) {
                return new TableCell<>() {

                    private Button btn =
                            new Button("");

                    {
                        btn.getStyleClass().add("deleteClientButton");
                        btn.setMinWidth(15);
                        btn.setPrefWidth(15);
                        btn.setOnAction(event -> {
                            getTableView().getItems().get(getIndex()).deleteServer(connServer);
                            lessonsData.remove(getTableView().getItems().get(getIndex()));
                            //clientsTable.refresh();
                            new Thread() {
                                @Override
                                public void run() {
                                    initDataFromServer();
                                    System.out.println("deleted");
                                }
                            }.start();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            String toSet;
                            if (currentLanguage.equals("English")) {
                                btn.setText("");
                            }
                            if (currentLanguage.equals("Russian")) {
                                btn.setText("");
                            }
                            setGraphic(btn);
                        }
                    }
                };
            }
        };
        Callback<TableColumn<Subject, Void>, TableCell<Subject, Void>> cellFactoryDeleteSubject = new Callback<>() {
            @Override
            public TableCell<Subject, Void> call(TableColumn<Subject, Void> param) {
                return new TableCell<>() {

                    private Button btn =
                            new Button("");

                    {
                        btn.getStyleClass().add("deleteClientButton");
                        btn.setMinWidth(15);
                        btn.setPrefWidth(15);
                        btn.setOnAction(event -> {
                            getTableView().getItems().get(getIndex()).deleteServer(connServer);
                            lessonsData.remove(getTableView().getItems().get(getIndex()));
                            //clientsTable.refresh();
                            new Thread() {
                                @Override
                                public void run() {
                                    initDataFromServer();
                                    System.out.println("deleted");
                                }
                            }.start();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            String toSet;
                            if (currentLanguage.equals("English")) {
                                btn.setText("");
                            }
                            if (currentLanguage.equals("Russian")) {
                                btn.setText("");
                            }
                            setGraphic(btn);
                        }
                    }
                };
            }
        };

        maritalStatusColumn.setCellFactory(cellFactory1);
        disabilityColumn.setCellFactory(cellFactory2);
        retireeColumn.setCellFactory(cellFactory3);

        deleteLessonColumn.setCellFactory(cellFactoryDeleteLesson);
        deleteTeacherSubjectColumn.setCellFactory(cellFactoryDeleteTeacherSubject);
        deleteGroupColumn.setCellFactory(cellFactoryDeleteGroup);
        deleteStudentColumn.setCellFactory(cellFactoryDeleteStudent);
        deleteTeacherColumn.setCellFactory(cellFactoryDeleteTeacher);
        deleteSubjectColumn.setCellFactory(cellFactoryDeleteSubject);

        lessonsTable.getColumns().add(0, deleteLessonColumn);
        teachersSubjectsTable.getColumns().add(0, deleteTeacherSubjectColumn);
        groupsTable.getColumns().add(0, deleteGroupColumn);
        studentsTable.getColumns().add(0, deleteStudentColumn);
        teachersTable.getColumns().add(0, deleteTeacherColumn);
        subjectsTable.getColumns().add(0, deleteSubjectColumn);

//        clientsTable.getColumns().add(18, maritalStatusColumn);
//        clientsTable.getColumns().add(18, disabilityColumn);
//        clientsTable.getColumns().add(18, retireeColumn);
//        clientsTable.getColumns().add(0, deleteColumn);
    }

    private boolean checkEmail(TextField toCheck) {
        if (!toCheck.getText().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+))") && !toCheck.getText().equals("")) {
            toCheck.setStyle("-fx-border-color: rgb(255,13,19)");
            return false;
        }
        return true;
    }

    private boolean checkUsernamePassword(TextField toCheck) {
        if (toCheck.getText().length() < 3) {
            toCheck.setStyle("-fx-border-color: rgb(255,13,19)");
            return false;
        }
        return true;
    }

    private boolean checkDeleteField(TextField toCheck) {
        if (!toCheck.getText().matches("^[0-9]+(,[0-9]+)*$")) {
            toCheck.setStyle("-fx-border-color: rgb(255,13,19)");
            return false;
        }
        return true;
    }

    private void searchUser() {
        initUsersDataServerBuffer();
        if (!searchField.getText().equals("")) {
            Iterator<User> i = usersData.iterator();
            switch (criteriaButton.getText()) {
                case "Id":
                    while (i.hasNext()) {
                        if (i.next().getId() != Integer.parseInt(searchField.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Access":
                    while (i.hasNext()) {
                        if (i.next().getAccessMode() != Integer.parseInt(searchField.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Username":
                    while (i.hasNext()) {
                        if (!i.next().getUsername().equals(searchField.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Password":
                    while (i.hasNext()) {
                        if (!i.next().getPassword().equals(searchField.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "E-mail":
                    while (i.hasNext()) {
                        if (!i.next().getEmail().equals(searchField.getText())) {
                            i.remove();
                        }
                    }
                    break;
            }
        }
    }

    private void searchLesson() {
        initUsersDataServerBuffer();
        if (!searchFieldLesson.getText().equals("")) {
            Iterator<Lesson> i = lessonsData.iterator();
            switch (criteriaButtonLesson.getText()) {
                case "ID":
                    while (i.hasNext()) {
                        if (i.next().getId() != Integer.parseInt(searchFieldLesson.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "ID группы":
                    while (i.hasNext()) {
                        if (!i.next().getGroupId().equals(searchFieldLesson.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "ID лектора":
                    while (i.hasNext()) {
                        if (!i.next().getTeacher_subjectId().equals(searchFieldLesson.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Аудитория":
                    while (i.hasNext()) {
                        if (!i.next().getCabinet().equals(searchFieldLesson.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Дата":
                    while (i.hasNext()) {
                        if (!i.next().getDate().equals(searchFieldLesson.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Время":
                    while (i.hasNext()) {
                        if (!i.next().getTime().equals(searchFieldLesson.getText())) {
                            i.remove();
                        }
                    }
                    break;
            }
        }
    }

    private void searchTeacherSubject() {
        initUsersDataServerBuffer();
        if (!searchFieldTeacherSubject.getText().equals("")) {
            Iterator<TeacherSubject> i = teachersSubjectsData.iterator();
            switch (criteriaButtonTeacherSubject.getText()) {
                case "ID":
                    while (i.hasNext()) {
                        if (i.next().getId() != Integer.parseInt(searchFieldTeacherSubject.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "ID учителя":
                    while (i.hasNext()) {
                        if (!i.next().getTeacherId().equals(searchFieldTeacherSubject.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "ID предмета":
                    while (i.hasNext()) {
                        if (!i.next().getSubjectId().equals(searchFieldTeacherSubject.getText())) {
                            i.remove();
                        }
                    }
                    break;
            }
        }
    }

    private void searchGroup() {
        initUsersDataServerBuffer();
        if (!searchFieldGroup.getText().equals("")) {
            Iterator<Group> i = groupsData.iterator();
            switch (criteriaButtonGroup.getText()) {
                case "ID":
                    while (i.hasNext()) {
                        if (i.next().getId() != Integer.parseInt(searchFieldGroup.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Уровень":
                    while (i.hasNext()) {
                        if (!i.next().getLevel().equals(searchFieldGroup.getText())) {
                            i.remove();
                        }
                    }
                    break;
            }
        }
    }

    private void searchStudent() {
        initUsersDataServerBuffer();
        if (!searchFieldStudent.getText().equals("")) {
            Iterator<Student> i = studentsData.iterator();
            switch (criteriaButtonStudent.getText()) {
                case "ID":
                    while (i.hasNext()) {
                        if (i.next().getId() != Integer.parseInt(searchFieldStudent.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Имя":
                    while (i.hasNext()) {
                        if (!i.next().getName().equals(searchFieldStudent.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Фамилия":
                    while (i.hasNext()) {
                        if (!i.next().getSurname().equals(searchFieldStudent.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Отчество":
                    while (i.hasNext()) {
                        if (!i.next().getPatronymic().equals(searchFieldStudent.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "ID группы":
                    while (i.hasNext()) {
                        if (!i.next().getGroupId().equals(searchFieldStudent.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Эл. почта":
                    while (i.hasNext()) {
                        if (!i.next().getEmail().equals(searchFieldStudent.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Телефон":
                    while (i.hasNext()) {
                        if (i.next().getId() != Integer.parseInt(searchFieldStudent.getText())) {
                            i.remove();
                        }
                    }
                    break;
            }
        }
    }

    private void searchTeacher() {
        initUsersDataServerBuffer();
        if (!searchFieldTeacher.getText().equals("")) {
            Iterator<Teacher> i = teachersData.iterator();
            switch (criteriaButtonTeacher.getText()) {
                case "ID":
                    while (i.hasNext()) {
                        if (i.next().getId() != Integer.parseInt(searchFieldTeacher.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Имя":
                    while (i.hasNext()) {
                        if (!i.next().getName().equals(searchFieldTeacher.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Фамилия":
                    while (i.hasNext()) {
                        if (!i.next().getSurname().equals(searchFieldTeacher.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Отчество":
                    while (i.hasNext()) {
                        if (!i.next().getPatronymic().equals(searchFieldTeacher.getText())) {
                            i.remove();
                        }
                    }
                    break;
            }
        }
    }

    private void searchSubject() {
        initUsersDataServerBuffer();
        if (!searchFieldSubject.getText().equals("")) {
            Iterator<Subject> i = subjectsData.iterator();
            switch (criteriaButtonSubject.getText()) {
                case "ID":
                    while (i.hasNext()) {
                        if (i.next().getId() != Integer.parseInt(searchFieldSubject.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Название":
                    while (i.hasNext()) {
                        if (!i.next().getName().equals(searchFieldSubject.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "К-во часов":
                    while (i.hasNext()) {
                        if (!i.next().getHours().equals(searchFieldSubject.getText())) {
                            i.remove();
                        }
                    }
                    break;
            }
        }
    }

    private void searchClient() {
        //initClientsDataServerBuffer();
        if (!searchFieldClient.getText().equals("")) {
            Iterator<Client> i = clientsData.iterator();
            switch (criteriaButtonClient.getText()) {
                case "ID":
                    while (i.hasNext()) {
                        if (i.next().getId() != Integer.parseInt(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Name":
                case "Имя":
                    while (i.hasNext()) {
                        if (!i.next().getName().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Surname":
                case "Фамилия":
                    while (i.hasNext()) {
                        if (!i.next().getSurname().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Patronymic":
                case "Отчество":
                    while (i.hasNext()) {
                        if (!i.next().getPatronymic().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Full name":
                case "ФИО":
                    String[] buffer = searchFieldClient.getText().split(" ");
                    while (i.hasNext()) {
                        if (!i.next().getSurname().equals(buffer[0])
                                || !i.next().getName().equals(buffer[1])
                                || !i.next().getPatronymic().equals(buffer[2])) {
                            i.remove();
                        }
                    }
                    break;
                case "Passport series":
                case "Серия паспорта":
                    while (i.hasNext()) {
                        if (!i.next().getPassportSeries().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Passport number":
                case "Номер паспорта":
                    while (i.hasNext()) {
                        if (!i.next().getPassportNumber().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Issued by":
                case "Орган выдачи":
                    while (i.hasNext()) {
                        if (!i.next().getIssuedBy().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Issued date":
                case "Дата выдачи":
                    while (i.hasNext()) {
                        if (!i.next().getIssuedDate().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Birth date":
                case "Дата рождения":
                    while (i.hasNext()) {
                        if (!i.next().getBirthDate().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Birth place":
                case "Место рождения":
                    while (i.hasNext()) {
                        if (!i.next().getBirthPlace().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "ID number":
                case "Идент. номер":
                    while (i.hasNext()) {
                        if (!i.next().getIdNumber().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Citizenship":
                case "Гражданство":
                    while (i.hasNext()) {
                        if (!i.next().getCitizenship().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Act. residence city":
                case "Город проживания":
                    while (i.hasNext()) {
                        if (!i.next().getActualResidenceCity().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Act. residence address":
                case "Адрес проживания":
                    while (i.hasNext()) {
                        if (!i.next().getActualResidenceAddress().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Registration city":
                case "Город прописки":
                    while (i.hasNext()) {
                        if (!i.next().getRegistrationCity().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Job":
                case "Место работы":
                    while (i.hasNext()) {
                        if (!i.next().getJob().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Position":
                case "Должность":
                    while (i.hasNext()) {
                        if (!i.next().getPosition().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "E-Mail":
                    while (i.hasNext()) {
                        if (!i.next().getEmail().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Home phone":
                case "Домашний телефон":
                    while (i.hasNext()) {
                        if (!i.next().getHomeNumber().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Mobile phone":
                case "Мобильный телефон":
                    while (i.hasNext()) {
                        if (!i.next().getMobileNumber().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Disability":
                case "Инвалидность":
                    while (i.hasNext()) {
                        if (!i.next().getDisability().toString().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Retiree":
                case "Пенсионер":
                    while (i.hasNext()) {
                        if (!i.next().getRetiree().toString().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Monthly income":
                case "Месячный доход":
                    while (i.hasNext()) {
                        if (i.next().getMonthlyIncome().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;
                case "Marital status":
                case "Семейное положение":
                    while (i.hasNext()) {
                        if (!i.next().getMaritalStatus().toString().equals(searchFieldClient.getText())) {
                            i.remove();
                        }
                    }
                    break;

            }
        }
    }

    private void addLesson() {
        initDataFromServer();
        System.out.println("addLesson");
        boolean result = true;
        if (!addLessonTeacherSubjectIdTextField.getText().trim().matches("[0-9]+") || !teacherSubjectExists(Integer.parseInt(addLessonTeacherSubjectIdTextField.getText().trim())))
            result = false;
        if (!addLessonGroupIdTextField.getText().trim().matches("[0-9]+") || !groupExists(Integer.parseInt(addLessonGroupIdTextField.getText().trim())))
            result = false;
        if (!addLessonCabinetTextField.getText().trim().matches("[0-9]+"))
            result = false;
        if (!addLessonDateTextField.getText().trim().matches("^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$"))
            result = false;
        if (!addLessonTimeTextField.getText().trim().matches("(?:[01]\\d|2[0123]):(?:[012345]\\d):(?:[012345]\\d)"))
            result = false;

        if (result) {
            System.out.println("Good");
            Lesson toAdd = new Lesson();
            //TODO: добавить сеттеры в функции добавления

            toAdd.setTeacher_subjectId(addLessonTeacherSubjectIdTextField.getText().trim());
            toAdd.setGroupId(addLessonGroupIdTextField.getText().trim());
            toAdd.setCabinet(addLessonCabinetTextField.getText().trim());
            toAdd.setDate(addLessonDateTextField.getText().trim());
            toAdd.setTime(addLessonTimeTextField.getText().trim());

            connServer.sendString("addLesson|" + toAdd.toString());
            new Thread() {
                @Override
                public void run() {
                    initDataFromServer();
                    System.out.println("added");
                }
            }.start();
        } else {
            System.out.println("Not good");
        }
    }

    private void addTeacherSubject() {
        initDataFromServer();
        System.out.println("addTeacherSubject");
        boolean result = true;
        if (!addTeacherSubjectTeacherIdTextField.getText().trim().matches("[0-9]+") || !teacherExists(Integer.parseInt(addTeacherSubjectTeacherIdTextField.getText().trim())))
            result = false;
        if (!addTeacherSubjectSubjectIdTextField.getText().trim().matches("[0-9]+") || !subjectExists(Integer.parseInt(addTeacherSubjectSubjectIdTextField.getText().trim())))
            result = false;

        if (result) {
            System.out.println("Good");
            TeacherSubject toAdd = new TeacherSubject();

            toAdd.setTeacherId(addTeacherSubjectTeacherIdTextField.getText().trim());
            toAdd.setSubjectId(addTeacherSubjectSubjectIdTextField.getText().trim());

            connServer.sendString("addTeacherSubject|" + toAdd.toString());
            new Thread() {
                @Override
                public void run() {
                    initDataFromServer();
                    System.out.println("added");
                }
            }.start();
        } else {
            System.out.println("Not good");
        }
    }

    private void addGroup() {
        System.out.println("addGroup");
        boolean result = true;
        if (!addGroupLevelTextField.getText().trim().matches("[ABC]"))
            result = false;

        if (result) {
            System.out.println("Good");
            Group toAdd = new Group();

            toAdd.setLevel(addGroupLevelTextField.getText());

            connServer.sendString("addGroup|" + toAdd.toString());
            new Thread() {
                @Override
                public void run() {
                    initDataFromServer();
                    System.out.println("added");
                }
            }.start();
        } else {
            System.out.println("Not good");
        }
    }

    private void addStudent() {
        initDataFromServer();
        System.out.println("addStudent");
        boolean result = true;

        if (!addStudentNameTextField.getText().trim().matches("[а-яА-Я]{2,20}"))
            result = false;
        if (!addStudentSurnameTextField.getText().trim().matches("[а-яА-Я]{2,20}"))
            result = false;
        if (!addStudentPatronymicTextField.getText().trim().matches("[а-яА-Я]{2,30}"))
            result = false;
        if (!addStudentGroupIdTextField.getText().trim().matches("[0-9]+") || !groupExists(Integer.parseInt(addStudentGroupIdTextField.getText().trim())))
            result = false;
        if (!addStudentEmailTextField.getText().trim().matches("(?:[a-z0-9!_-]+(?:\\.[a-z0-9!_-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+))") && !addStudentEmailTextField.getText().equals(""))
            result = false;
        if (!addStudentPhoneTextField.getText().trim().matches("^(\\+375|375)?[\\s\\-]?\\(?(17|29|33|44)\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$") && !addStudentPhoneTextField.getText().equals(""))
            result = false;


        if (result) {
            System.out.println("Good");
            Student toAdd = new Student();

            toAdd.setName(addStudentNameTextField.getText().trim());
            toAdd.setSurname(addStudentSurnameTextField.getText().trim());
            toAdd.setPatronymic(addStudentPatronymicTextField.getText().trim());
            toAdd.setGroupId(addStudentGroupIdTextField.getText().trim());
            toAdd.setEmail(addStudentEmailTextField.getText().trim());
            toAdd.setPhone(addStudentPhoneTextField.getText().trim());

            System.out.println(toAdd);
            connServer.sendString("addStudent|" + toAdd.toString());
            new Thread() {
                @Override
                public void run() {
                    initDataFromServer();

                    System.out.println("added");
                }
            }.start();
        } else {
            System.out.println("Not good");
        }
    }

    public boolean groupExists(int id) {
        for (Group g : groupsData)
            if (g.getId() == id)
                return true;
        return false;
    }

    public boolean teacherExists(int id) {
        for (Teacher t : teachersData)
            if (t.getId() == id)
                return true;
        return false;
    }

    public boolean subjectExists(int id) {
        for (Subject s : subjectsData)
            if (s.getId() == id)
                return true;
        return false;
    }

    public boolean teacherSubjectExists(int id) {
        for (TeacherSubject ts : teachersSubjectsData)
            if (ts.getId() == id)
                return true;
        return false;
    }

    private void addTeacher() {
        System.out.println("addTeacher");
        boolean result = true;

        if (!addTeacherNameTextField.getText().trim().matches("[а-яА-Я]{2,20}"))
            result = false;
        if (!addTeacherSurnameTextField.getText().trim().matches("[а-яА-Я]{2,20}"))
            result = false;
        if (!addTeacherPatronymicTextField.getText().trim().matches("[а-яА-Я]{2,30}"))
            result = false;

        if (result) {
            System.out.println("Good");
            Teacher toAdd = new Teacher();

            toAdd.setName(addTeacherNameTextField.getText().trim());
            toAdd.setSurname(addTeacherSurnameTextField.getText().trim());
            toAdd.setPatronymic(addTeacherPatronymicTextField.getText().trim());

            connServer.sendString("addTeacher|" + toAdd.toString());
            new Thread() {
                @Override
                public void run() {
                    initDataFromServer();
                    System.out.println("added");
                }
            }.start();
        } else {
            System.out.println("Not good");
        }
    }

    private void addSubject() {
        System.out.println("addSubject");
        boolean result = true;

        if (!addSubjectNameTextField.getText().trim().matches("[а-яА-Я]{2,30}"))
            result = false;
        if (!addSubjectHoursTextField.getText().trim().matches("[0-9]+"))
            result = false;

        if (result) {
            System.out.println("Good");
            Subject toAdd = new Subject();

            toAdd.setName(addSubjectNameTextField.getText().trim());
            toAdd.setHours(addSubjectHoursTextField.getText().trim());

            connServer.sendString("addSubject|" + toAdd.toString());
            new Thread() {
                @Override
                public void run() {
                    initDataFromServer();
                    System.out.println("added");
                }
            }.start();
        } else {
            System.out.println("Not good");
        }
    }

    private boolean addClientFillingError(TextField field, Label description) {
        field.setStyle("-fx-border-color: rgb(255,13,19)");
        if (field.getText().equals(""))
            description.setText(currentLanguage.equals("English") ? "Obligatory field" : "Обязательное поле");
        else
            description.setText(currentLanguage.equals("English") ? "Wrong format" : "Неверный формат");
        return false;
    }

    private boolean isIDNumberUnique(TextField idNumber, Label description) {
        for (Client c : clientsData) {
            if (c.getIdNumber().equals(idNumber.getText().trim())) {
                description.setText(currentLanguage.equals("English") ? "Non-unique ID" : "Неуникальный ID");
                idNumber.setStyle("-fx-border-color: rgb(255,13,19)");
                return false;
            }
        }
        return true;
    }

    private boolean isIDNumberUnique(String idNumber) {
        for (Client c : clientsData) {
            if (c.getIdNumber().equals(idNumber.trim())) {
                return false;
            }
        }
        return true;
    }

    private boolean isPassportNumberUnique(TextField passNum, Label description) {
        for (Client c : clientsData) {
            if (c.getPassportNumber().equals(passNum.getText().trim())) {
                description.setText(currentLanguage.equals("English") ? "Non-unique number" : "Неуникальный номер");
                passNum.setStyle("-fx-border-color: rgb(255,13,19)");
                return false;
            }
        }
        return true;
    }

    private boolean isPassportNumberUnique(String passNum) {
        for (Client c : clientsData) {
            if (c.getPassportNumber().equals(passNum.trim())) {
                return false;
            }
        }
        return true;
    }

    private boolean isFullnameUnique(TextField name, TextField surname, TextField patro, Label description) {
        for (Client c : clientsData) {
            if (c.getName().equals(name.getText().trim()) && c.getSurname().equals(surname.getText().trim()) && c.getPatronymic().equals(patro.getText().trim())) {
                description.setText(currentLanguage.equals("English") ? "Non-unique fullname" : "Неуникальное ФИО");
                return false;
            }
        }
        description.setText("");
        return true;
    }

    private boolean isFullnameUnique(String name, String surname, String patro) {
        for (Client c : clientsData) {
            if (c.getName().equals(name) && c.getSurname().equals(surname) && c.getPatronymic().equals(patro)) {
                return false;
            }
        }
        return true;
    }


    private synchronized void initDataFromServer() {
        if (!connServer.exists())
            loginBegin();
        else {
            connServer.sendString("init");
            for (int i = 0; i < 10; i++) {
                if (!connServer.isInProcess()) {
                    initUsersDataServerBuffer();
                    initGroupsDataServerBuffer();
                    initLessonsDataServerBuffer();
                    initStudentsDataServerBuffer();
                    initSubjectsDataServerBuffer();
                    initTeachersDataServerBuffer();
                    initTeachersSubjectsDataServerBuffer();

                    System.out.println("USERS :");
                    for (User u : usersData)
                        System.out.println(u);
                    System.out.println("GROUPS :");
                    for (Group g : groupsData)
                        System.out.println(g);
                    System.out.println("LESSONS :");
                    for (Lesson l : lessonsData)
                        System.out.println(l);
                    System.out.println("STUDENTS :");
                    for (Student s : studentsData)
                        System.out.println(s);
                    System.out.println("SUBJECTS :");
                    for (Subject sb : subjectsData)
                        System.out.println(sb);
                    System.out.println("TEACHERS :");
                    for (Teacher t : teachersData)
                        System.out.println(t);
                    System.out.println("TEACHERS SUBJECTS :");
                    for (TeacherSubject ts : teachersSubjectsData)
                        System.out.println(ts);
                    break;
                }
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // TODO:
}
