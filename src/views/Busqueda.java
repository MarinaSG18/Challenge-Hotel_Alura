package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import controller.HuespedController;
import controller.ReservacionController;
import modelo.Huesped;
import modelo.Reservacion;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.List;
import java.util.Optional;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Date;
import java.awt.Cursor;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modeloReservacion;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;

	private ReservacionController reservacionController;
	private HuespedController huespedController;
	private JScrollPane scroll_tableReservas;
	JTabbedPane panel;
	JLabel lblAdvertencia = new JLabel("");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {

		contenido();
	}

	public Busqueda(String viewModificacion) {

		contenido();

		if (viewModificacion == "huesped") {
			panel.setSelectedIndex(1);
		} else if (viewModificacion == "reservacion") {
			panel.setSelectedIndex(0);
		}

	}

	private void contenido() {
		reservacionController = new ReservacionController();
		huespedController = new HuespedController();
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(257, 62, 354, 42);
		contentPane.add(lblNewLabel_4);

		panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		tbReservas = new JTable() {

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));

//		tbReservas.addMouseListener( new MouseAdapter () {
//		    @Override
//		    public void mouseClicked ( MouseEvent e ) {
//		        int columnIndex = tbReservas.getSelectedColumn ();
//		        System.out.println ( "Double click on jtable" );
//		        if ( columnIndex == 0 || columnIndex == 3) {
//		            JOptionPane.showMessageDialog ( getParent() , "No se puede modificar este valor" , "Error Edicion No Permitida" , JOptionPane.ERROR_MESSAGE );
//		        }
//		    }
//		});
//		
		modeloReservacion = (DefaultTableModel) tbReservas.getModel();
		modeloReservacion.addColumn("Numero de Reserva");
		modeloReservacion.addColumn("Fecha Check In");
		modeloReservacion.addColumn("Fecha Check Out");
		modeloReservacion.addColumn("Valor");
		modeloReservacion.addColumn("Forma de Pago");

		cargarTablaReservacionSinFiltro();
		scroll_tableReservas = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")),
				scroll_tableReservas, null);
		scroll_tableReservas.setVisible(true);

		tbHuespedes = new JTable() {

			public boolean isCellEditable(int row, int column) {
				// return (column != 0 && column != 6);
				return false;
			};
		};
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");

		cargarTablaHuespedSinFiltro();
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")),
				scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);

		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);

			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);

		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);

		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) { // Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) { // Al usuario quitar el mouse por el botón este volverá al estado
													// original
				btnexit.setBackground(Color.white);
				labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);

		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);

		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				boolean numero = txtBuscar.getText().matches(".*\\d.*");

				if (numero) {
					limpiarTabla(modeloReservacion);
					cargarTablaReservacionPorFiltro();
				} else {

					limpiarTabla(modeloHuesped);
					cargarTablaHuespedPorFiltro();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnbuscar.setBackground(new Color(118, 187, 223));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnbuscar.setBackground(new Color(12, 138, 199));
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);

		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

		JPanel btnEditar = new JPanel();
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (panel.getSelectedIndex() == 1) {
					modificarHuesped();
					limpiarTabla(modeloHuesped);
					cargarTablaHuespedSinFiltro();
				}

				if (panel.getSelectedIndex() == 0) {
					modificarReservacion();
					limpiarTabla(modeloReservacion);
					cargarTablaReservacionSinFiltro();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnEditar.setBackground(new Color(0, 153, 51));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnEditar.setBackground(new Color(12, 138, 199));
			}
		});
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);

		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);

		JPanel btnEliminar = new JPanel();
		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (panel.getSelectedIndex() == 1) {
					eliminarHuesped();
					limpiarTabla(modeloHuesped);
					cargarTablaHuespedSinFiltro();
				}

				if (panel.getSelectedIndex() == 0) {
					eliminarReservacion();
					limpiarTabla(modeloReservacion);
					cargarTablaReservacionSinFiltro();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) { // Al usuario pasar el mouse por el botón este cambiará de color
				btnEliminar.setBackground(Color.red);

			}

			@Override
			public void mouseExited(MouseEvent e) { // Al usuario quitar el mouse por el botón este volverá al estado
													// original
				btnEliminar.setBackground(new Color(12, 138, 199));
			}

		});
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);

		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);

		JPanel btnMostrar = new JPanel();
		btnMostrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (panel.getSelectedIndex() == 0) {
					limpiarTabla(modeloReservacion);
					cargarTablaReservacionSinFiltro();
				}

				if (panel.getSelectedIndex() == 1) {
					limpiarTabla(modeloHuesped);
					cargarTablaHuespedSinFiltro();
				}		

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnMostrar.setBackground(new Color(118, 187, 223));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnMostrar.setBackground(new Color(12, 138, 199));
			}
		});
		btnMostrar.setLayout(null);
		btnMostrar.setBackground(new Color(12, 138, 199));
		btnMostrar.setBounds(382, 508, 154, 35);
		contentPane.add(btnMostrar);

		JLabel lblMostrar = new JLabel("MOSTRAR TODOS");
		lblMostrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblMostrar.setHorizontalAlignment(SwingConstants.CENTER);
		lblMostrar.setForeground(Color.WHITE);
		lblMostrar.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblMostrar.setBounds(0, 0, 154, 35);
		btnMostrar.add(lblMostrar);
		lblAdvertencia.setVisible(false);

		lblAdvertencia.setForeground(Color.RED);
		lblAdvertencia.setBounds(30, 508, 220, 20);
		contentPane.add(lblAdvertencia);
		setResizable(false);
	}

//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}
	
	private void mostrarMensajeTemporal(String mensaje, int duracion) {
		lblAdvertencia.setText(mensaje);
		lblAdvertencia.setVisible(true);
		Timer timer = new Timer(duracion, (ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblAdvertencia.setVisible(false);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	private void limpiarTabla(DefaultTableModel modelo) {
		modelo.getDataVector().clear();
	}

	private boolean tieneFilaElegida(JTable tablaSeleccionada) {
		return tablaSeleccionada.getSelectedRowCount() == 0 || tablaSeleccionada.getSelectedColumnCount() == 0;
	}

	/*
	 * Cargar Datos de la tabla Rerservaciones
	 */
	private void cargarTablaReservacion(boolean hayFiltro, String filtro) {

		List<Reservacion> reservaciones = this.reservacionController.listar();

		if (hayFiltro) {
			reservaciones = this.reservacionController.listar(Integer.valueOf(filtro));
		} else {
			reservaciones = this.reservacionController.listar();
		}

		reservaciones.forEach(reservacion -> modeloReservacion
				.addRow(new Object[] { reservacion.getId(), reservacion.getFechaEntrada(), reservacion.getFechaSalida(),
						reservacion.getValor(), reservacion.getFormaPago() }));

		if (reservaciones.isEmpty()) {
			mostrarMensajeTemporal("No existe el N° de reservación '" + txtBuscar.getText() + "' ", 3000);
		}
	}

	private void cargarTablaReservacionSinFiltro() {
		cargarTablaReservacion(false, null);
	}

	private void cargarTablaReservacionPorFiltro() {
		boolean porNumReservacion = true;
		cargarTablaReservacion(porNumReservacion, txtBuscar.getText());

	}

	/*
	 * Cargar Datos de la tabla Huespedes
	 */

	private void cargarTablaHuesped(boolean hayFiltro, String filtro) {
		List<Huesped> huespedes;

		if (hayFiltro) {
			huespedes = this.huespedController.listarPorApellido(filtro);

		} else {
			huespedes = this.huespedController.listar();

		}

		huespedes.forEach(huesped -> modeloHuesped.addRow(new Object[] { huesped.getId(), huesped.getNombre(),
				huesped.getApellido(), huesped.getFechaNacimiento(), huesped.getNacionalidad(), huesped.getTelefono(),
				huesped.getIdReserva() }));

		if (huespedes.isEmpty()) {
			mostrarMensajeTemporal("No se ha encontrado el apellido '" + txtBuscar.getText() + "' ", 3000);
		}
	}


	private void cargarTablaHuespedSinFiltro() {
		cargarTablaHuesped(false, null);
	}

	private void cargarTablaHuespedPorFiltro() {

		cargarTablaHuesped(true, txtBuscar.getText());
	}

	/*
	 * Modificar Datos de la Tabla REservaciones
	 */

	private void modificarReservacion() {
		if (tieneFilaElegida(tbReservas)) {
			JOptionPane.showMessageDialog(this, "Por favor, elije un item");
			return;
		}

		Optional.ofNullable(

				modeloReservacion.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
				.ifPresentOrElse(fila -> {

					Integer id = Integer
							.valueOf(modeloReservacion.getValueAt(tbReservas.getSelectedRow(), 0).toString());
					Date fechaEntrada = (Date) modeloReservacion.getValueAt(tbReservas.getSelectedRow(), 1);
					Date fechaSalida = (Date) modeloReservacion.getValueAt(tbReservas.getSelectedRow(), 2);
					Double valor = Double
							.valueOf(modeloReservacion.getValueAt(tbReservas.getSelectedRow(), 3).toString());
					String formaPago = (String) modeloReservacion.getValueAt(tbReservas.getSelectedRow(), 4);

					Reservacion reservacion = new Reservacion(id, fechaEntrada, fechaSalida, valor, formaPago);
					ReservasView reservasView = new ReservasView(reservacion);
					reservasView.setVisible(true);
					dispose();

				}

						, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));

	}

	/*
	 * Modificar Datos de la Tabla Huespedes
	 */

	private void modificarHuesped() {
		if (tieneFilaElegida(tbHuespedes)) {
			JOptionPane.showMessageDialog(this, "Por favor, elije un item");
			return;
		}

		Optional.ofNullable(

				modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
				.ifPresentOrElse(fila -> {

					Integer id = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());
					String nombre = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 1);
					String apellido = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 2);
					Date fechaNacimiento = (Date) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3);
					String nacionalidad = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4);
					String telefono = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5);

					Integer id_reservacion = Integer
							.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 6).toString());
					
					Huesped huespedSelec = new Huesped(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono,
							id_reservacion);

					RegistroHuesped registro = new RegistroHuesped(huespedSelec);
					registro.setVisible(true);
					dispose();

				}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));

	}

	/*
	 * Eliminar datos de la Tablas
	 */

	private void eliminarElemento(JTable tabla, DefaultTableModel modelo, Object controller, String tipo) {
		if (tieneFilaElegida(tabla)) {
			JOptionPane.showMessageDialog(this, "Por favor, elige un item");
			return;
		}

		Optional.ofNullable(modelo.getValueAt(tabla.getSelectedRow(), 0)).ifPresentOrElse(fila -> {
			Integer id = Integer.valueOf(fila.toString());
			int filasEliminadas = 0;

			if (controller instanceof ReservacionController) {
				filasEliminadas = ((ReservacionController) controller).eliminar(id);
			} else if (controller instanceof HuespedController) {
				filasEliminadas = ((HuespedController) controller).eliminar(id);
			}

			JOptionPane.showMessageDialog(this, String.format("%d %s eliminado con éxito!", filasEliminadas, tipo));
		}, () -> JOptionPane.showMessageDialog(this, "Por favor, elige un item"));
	}

	private void eliminarReservacion() {
		eliminarElemento(tbReservas, modeloReservacion, reservacionController, "reservación");
	}

	private void eliminarHuesped() {
		eliminarElemento(tbHuespedes, modeloHuesped, huespedController, "huesped");
	}
}
