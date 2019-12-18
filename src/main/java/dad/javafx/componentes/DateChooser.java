package dad.javafx.componentes;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import java.time.Year;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import javafx.scene.layout.GridPane;

public class DateChooser extends GridPane implements Initializable {
	// Model
	private ListProperty<String> meses = new SimpleListProperty<String>(FXCollections.observableArrayList());
	private ListProperty<Integer> dias = new SimpleListProperty<Integer>(FXCollections.observableArrayList());
	private ListProperty<String> annios = new SimpleListProperty<String>(FXCollections.observableArrayList());
	private IntegerProperty mesSeleccionado = new SimpleIntegerProperty();
	private StringProperty annioSeleccionado = new SimpleStringProperty();
	private ObjectProperty<LocalDate> date = new SimpleObjectProperty<LocalDate>();

	public DateChooser() {
		super();
		try {
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DateChooserView.fxml"));
			 loader.setController(this);
			 loader.setRoot(this);
			 loader.load();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addMeses() {
		meses.addAll("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre",
				"Noviembre", "Diciembre");
	}

	private void addDias() {
		for (int i = 1; i < 32; i++) {
			dias.add(i);
		}
	}

	private void addAnnios() {
		for (int i = LocalDate.now().getYear(); i >= 1900; i--) {
			annios.add(i + "");
		}
	}
	@FXML
	private ComboBox<Integer> comboDia;

	@FXML
	private ComboBox<String> comboMes;

	@FXML
	private ComboBox<String> comboAnnio;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addAnnios();
		addMeses();
		addDias();

		comboDia.itemsProperty().bind(dias);
		comboMes.itemsProperty().bind(meses);
		comboAnnio.itemsProperty().bind(annios);
		comboMes.getSelectionModel().select(LocalDate.now().getMonthValue() - 1);
		comboDia.getSelectionModel().select(LocalDate.now().getDayOfMonth() - 1);
		comboAnnio.getSelectionModel().select(annios.get(0));
		// diaSeleccionado.bind(comboDia.getSelectionModel().selectedIndexProperty());

		mesSeleccionado.bind(comboMes.getSelectionModel().selectedIndexProperty());
		annioSeleccionado.bind(comboAnnio.getSelectionModel().selectedItemProperty());
		mesSeleccionado.addListener((o, ov, nv) -> cambiosEnLista(nv));
		comboAnnio.valueProperty().addListener((o, ov, nv) -> cambiosEnAnnio(nv));
		comboDia.valueProperty().addListener((o, ov, nv) -> cambiosEnDias(nv));

	}

	private void cambiosEnDias(Integer nv) {
		if(nv != null) {
		dateChanges();
		}
	}

	private void cambiosEnAnnio(String nv) {
		int indiceSeleccionado = comboDia.getSelectionModel().getSelectedIndex();
		if (!nv.matches("\\d*")) {
			comboAnnio.setValue((nv.replaceAll("[^\\d]", "")));
			comboAnnio.setValue(String.valueOf(LocalDate.now().getYear()));
		} else if (mesSeleccionado.get() == 1) {
			if (Year.of(Integer.parseInt(annioSeleccionado.get())).isLeap()) {

				dias.clear();
				addDias();
				dias.remove(30);
				dias.remove(29);

			} else {
				dias.clear();
				addDias();
				dias.remove(30);
				dias.remove(29);
				dias.remove(28);

			}
			if (indiceSeleccionado > dias.size() - 1) {
				comboDia.getSelectionModel().select(dias.size() - 1);
			} else {
				comboDia.getSelectionModel().select(indiceSeleccionado);
			}

		}
		dateChanges();

	}

	private void cambiosEnLista(Number nv) {
		int indiceSeleccionado = comboDia.getSelectionModel().getSelectedIndex();
		if (nv.intValue() == 1) {
			int annio = (annioSeleccionado.get() != "") ? Integer.parseInt(annioSeleccionado.get())
					: LocalDate.now().getYear();
			if (Year.of(annio).isLeap()) {

				dias.clear();
				addDias();
				dias.remove(30);
				dias.remove(29);

			} else {
				dias.clear();
				addDias();
				dias.remove(30);
				dias.remove(29);
				dias.remove(28);

			}
		} else if (mesSeleccionado.get() != 0 && mesSeleccionado.get() != 6 && mesSeleccionado.get() != 7
				&& mesSeleccionado.get() % 2 != 0) {

			dias.clear();
			addDias();
			dias.remove(30);

		} else {
			dias.clear();
			addDias();
		}
		if (indiceSeleccionado > dias.size() - 1) {
			comboDia.getSelectionModel().select(dias.size() - 1);
		} else {
			comboDia.getSelectionModel().select(indiceSeleccionado);
		}
		dateChanges();

	}

	private void dateChanges() {
		
		String compruebaAnnio;
		if(comboAnnio.getSelectionModel().getSelectedItem().equals("")) {
		compruebaAnnio=String.valueOf(LocalDate.now().getYear());
		}else {
		compruebaAnnio=comboAnnio.getSelectionModel().getSelectedItem();
		}
		date.set(LocalDate.parse(compruebaAnnio + "-"
				+ (String.format("%02d", (comboMes.getSelectionModel().getSelectedIndex() + 1))) + "-"
				+ (String.format("%02d", (comboDia.getSelectionModel().getSelectedItem())))));
	}

	public final ListProperty<String> mesesProperty() {
		return this.meses;
	}

	public final ObservableList<String> getMeses() {
		return this.mesesProperty().get();
	}

	public final void setMeses(final ObservableList<String> meses) {
		this.mesesProperty().set(meses);
	}

	public final ListProperty<Integer> diasProperty() {
		return this.dias;
	}

	public final ObservableList<Integer> getDias() {
		return this.diasProperty().get();
	}

	public final void setDias(final ObservableList<Integer> dias) {
		this.diasProperty().set(dias);
	}

	public final ListProperty<String> anniosProperty() {
		return this.annios;
	}

	public final ObservableList<String> getAnnios() {
		return this.anniosProperty().get();
	}

	public final void setAnnios(final ObservableList<String> annios) {
		this.anniosProperty().set(annios);
	}

	public final ObjectProperty<LocalDate> dateProperty() {
		return this.date;
	}

	public final LocalDate getDate() {
		return this.dateProperty().get();
	}

	public final void setDate(final LocalDate date) {
		this.dateProperty().set(date);
	}



}
