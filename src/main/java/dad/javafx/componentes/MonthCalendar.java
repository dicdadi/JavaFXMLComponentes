package dad.javafx.componentes;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class MonthCalendar extends GridPane implements Initializable {

	// Model
	private StringProperty mesView = new SimpleStringProperty();
	private IntegerProperty month = new SimpleIntegerProperty();
	private IntegerProperty year = new SimpleIntegerProperty();
	private String[] months = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
			"Octubre", "Noviembre", "Diciembre" };

    @FXML
    private Label mesLabel;

	@FXML
	private Label day1;

	@FXML
	private Label day2;

	@FXML
	private Label day3;

	@FXML
	private Label day4;

	@FXML
	private Label day5;

	@FXML
	private Label day6;

	@FXML
	private Label day7;

	@FXML
	private Label day8;

	@FXML
	private Label day9;

	@FXML
	private Label day10;

	@FXML
	private Label day11;

	@FXML
	private Label day12;

	@FXML
	private Label day13;

	@FXML
	private Label day14;

	@FXML
	private Label day15;

	@FXML
	private Label day16;

	@FXML
	private Label day17;

	@FXML
	private Label day18;

	@FXML
	private Label day19;

	@FXML
	private Label day20;

	@FXML
	private Label day21;

	@FXML
	private Label day22;

	@FXML
	private Label day23;

	@FXML
	private Label day24;

	@FXML
	private Label day25;

	@FXML
	private Label day26;

	@FXML
	private Label day27;

	@FXML
	private Label day28;

	@FXML
	private Label day29;

	@FXML
	private Label day30;

	@FXML
	private Label day31;

	@FXML
	private Label day32;

	@FXML
	private Label day33;

	@FXML
	private Label day34;

	@FXML
	private Label day35;

	@FXML
	private Label day36;

	@FXML
	private Label day37;

	@FXML
	private Label day38;

	@FXML
	private Label day39;

	@FXML
	private Label day40;

	@FXML
	private Label day41;

	@FXML
	private Label day42;



	public MonthCalendar() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MonthCalendarView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mesLabel.textProperty().bind(mesView);
		
		year.addListener((o, ov, nv) -> onChangesListener());
		month.addListener((o, ov, nv) -> onChangesListener());
		
	}

	private void onChangesListener() {
		//Limpiamos el texto de todas las label.Las label que muestran número inician des 8 hasta 50
		Label f = new Label();

		for (int i = 8; i < 50; i++) {
			f = new Label();
			f = (Label)getChildren().get(i);
			f.setText("");
		}
		//Cambiamos el nombre del mes
		mesView.set(months[month.get()]);
		
		//Calculamos desde donde inicia nuestro mes y donde temrina, y vamos rellenando a partir de ahí
		Calendar calendar = Calendar.getInstance();
		calendar.set(year.get(), month.get(), 0);
		int dayStart = calendar.get(Calendar.DAY_OF_WEEK)+7;
		int dayStop = numeroDeDiasMes(month.get()+1, year.get());
		for (int i = 1; i <= dayStop; i++) {
			f = new Label();
			f = (Label) getChildren().get(dayStart);
			dayStart++;
			f.setText(i + "");
		}
	//Para mostrar el día actual
		Label lb = new Label();
		lb = (Label) getChildren().get(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 9);
		lb.getStyleClass().clear();
		int actualMonth = LocalDate.now().getMonthValue();
		if (LocalDate.now().getYear() == year.get() && actualMonth-1 == month.get()) {
			lb = new Label();
			lb = (Label) getChildren().get(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 9);
			lb.getStyleClass().clear();
			lb.getStyleClass().add("today");
		}

	}

	private static int numeroDeDiasMes(int mes, int annio) {

		int numeroDias=0;

		switch (mes) {
		case 1:
			numeroDias=31;
			break;
		case 2:

			if (esBisiesto(annio)) {
				numeroDias = 29;
			} else {
				numeroDias = 28;
			}
			break;
		case 3:
			numeroDias=31;
			break;
		case 4:
			numeroDias=30;
			break;
		case 5:
			numeroDias=31;
			break;
		case 6:
			numeroDias=30;
			break;
		case 7:
			numeroDias=31;
			break;
		case 8:
			numeroDias=31;
			break;
		case 9:
			numeroDias=30;
			break;
		case 10:
			numeroDias=31;
			break;
		case 11:
			numeroDias = 30;
			break;
		case 12:
			numeroDias = 31;
			break;
	

		}

		return numeroDias;
	}

	private static boolean esBisiesto(int anio) {

		GregorianCalendar calendar = new GregorianCalendar();
		boolean esBisiesto = false;
		if (calendar.isLeapYear(anio)) {
			esBisiesto = true;
		}
		return esBisiesto;

	}



	public final IntegerProperty monthProperty() {
		return this.month;
	}

	public final int getMonth() {
		return this.monthProperty().get();
	}

	public final void setMonth(final int month) {
		this.monthProperty().set(month);
	}

	public final IntegerProperty yearProperty() {
		return this.year;
	}

	public final int getYear() {
		return this.yearProperty().get();
	}

	public final void setYear(final int year) {
		this.yearProperty().set(year);
	}

}
