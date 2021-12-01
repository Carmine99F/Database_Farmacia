import javax.swing.JFrame;
import javax.swing.JTextField;



import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

import java.awt.Font;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class Farmacia {

    private JFrame frame;
    private JTextField utente;
    private JPasswordField passwordField;
    private JLabel lblFarmacia;

    private Statement state;
    private Connection con;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    Farmacia window = new Farmacia();
                    window.frame.setVisible(true);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     * @return
     */

    public Farmacia() {
        initialize();
    }
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        //String DB_URL="dbc:mysql://localhost:3306/Farmacia";
        String DB_URL="jdbc:mysql://127.0.0.1:3306/?user=root";
        frame = new JFrame();
        //frame.setResizable(false);
        frame.setBounds(100, 100, 591, 370);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(Color.DARK_GRAY);

        utente = new JTextField();
        utente.setFont(new Font("Tahoma", Font.PLAIN, 22));
        utente.setBounds(291, 106, 121, 33);
        frame.getContentPane().add(utente);
        utente.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 22));
        passwordField.setBounds(292, 168, 121, 33);
        frame.getContentPane().add(passwordField);

        JLabel lblUser = new JLabel("Utente:");
        lblUser.setForeground(Color.BLACK);
        lblUser.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblUser.setBounds(164, 106, 85, 33);
        lblUser.setForeground(Color.WHITE);
        frame.getContentPane().add(lblUser);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblPassword.setBounds(164, 168, 117, 33);
        lblPassword.setForeground(Color.WHITE);
        frame.getContentPane().add(lblPassword);

        JButton buttonLogin = new JButton("Login");
        buttonLogin.setForeground(Color.BLACK);
        buttonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {

                    con = DriverManager.getConnection(DB_URL,utente.getText(), passwordField.getText());

                    state = con.createStatement();

                    frame.setVisible(false);
                    // nuovo frame contenente quattro tasti insert,update,delete
                    JFrame insertUpdateDelete = new JFrame();
                    insertUpdateDelete.setBounds(100, 100, 591, 370);
                    //insertUpdateDelete.setForeground(Color.YELLOW);
                    insertUpdateDelete.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




                    insertUpdateDelete.setVisible(true);

                    //insertUpdateDelete.setSize(500, 400);

                    // creo area di testo
                    JTextArea area = new JTextArea();

                    try {
                        state = con.createStatement();


                    }
                    catch (SQLException e1) {
                        System.out.println(e1.getMessage());

                    }


                    //Visulizziamo i dati iniziali subito dopo il login
                    ResultSet result;
                    String risultato = "";
                    try {
                        result = state.executeQuery("select * from Ditta");
                        while (result.next()) {

                            risultato += result.getString("Identificativo") + " ";
                            risultato += result.getString("Recapito") + " ";
                            risultato += result.getString("Fax") + " ";
                            risultato += result.getString("Citta") + " ";
                            risultato += result.getString("Via") + " ";
                            risultato += result.getInt("NumeroCivico") + " ";
                            risultato+=result.getString("CAP") + "\n";

                        }


                        area.setText(risultato);

                    } catch (SQLException e2) {

                    }
                    // creo i quattro button
                    JButton insert = new JButton("Insert");
                    JButton delete = new JButton("Delete");
                    JButton update = new JButton("Update");
                    JButton select = new JButton("Select");

                    JPanel panel = new JPanel();
                    //panel.add(area);

                    // creo ActionListener per insert
                    class insertActionListener implements ActionListener {

                        public void actionPerformed(ActionEvent e) {


                            // Faccio inserire dall'utente
                            String Identificativo= "";
                            String Recapito="";
                            String Fax="";
                            String Citta="";
                            String Via="";
                            String NumeroCivico="";
                            String CAP="";



                            Identificativo = JOptionPane.showInputDialog("Identificativo ditta:");
                            Recapito = JOptionPane.showInputDialog("Recapito telefonico:");
                            Fax=JOptionPane.showInputDialog("Fax: ");
                            Citta=JOptionPane.showInputDialog("Inserisci cittÃ : ");
                            Via=JOptionPane.showInputDialog("Inserire via: ");
                            NumeroCivico=JOptionPane.showInputDialog("Numero civico: ");
                            Integer.parseUnsignedInt(NumeroCivico);
                            CAP=JOptionPane.showInputDialog("Inserisci il CAP: ");



                            //Creo la stringa per fare l'insert
                            String insertQuery = "INSERT INTO Ditta values (";
                            insertQuery += "\"" +Identificativo + "\"," + "\"" +Recapito+ "\","+"\"" +Fax+ "\","+"\"" + Citta+ "\","+"\"" +Via+ "\","+"\"" +NumeroCivico+ "\","+"\"" +CAP+"\""+");";
                            //Svolgo l'insert
                            ResultSet result;
                            try
                            {
                                state = con.createStatement();
                                state.executeUpdate(insertQuery);

                            }
                            catch (SQLException e1)
                            {
                                e1.printStackTrace();
                            }
                            String risultato = "";
                            //Mostro le modifiche
                            try
                            {
                                result = state.executeQuery("select * from Ditta");
                                while (result.next())
                                {

                                    risultato += result.getString("Identificativo") + " ";
                                    risultato += result.getString("Recapito") + " ";
                                    risultato += result.getString("Fax") + " ";
                                    risultato += result.getString("Citta") + " ";
                                    risultato += result.getString("Via") + " ";
                                    risultato += result.getInt("NumeroCivico") + " ";
                                    risultato+=result.getString("CAP") + "\n";


                                }

                                area.setText(risultato);

                            }
                            catch (SQLException e2)
                            {
                                System.out.println("Errore.");

                            }


                        }

                    }

                    // Creo l'actionListener per il delete
                    class deleteActionListener implements ActionListener {

                        public void actionPerformed(ActionEvent e) {
                            //Faccio inserire dall'utente la primary key per l'eliminazione
                            String Identificativo= JOptionPane.showInputDialog("Inserisci l'identificativo della ditta: ");
                            //creo la stringa SQL per la delete
                            String delete="Delete From Ditta where Identificativo ='" + Identificativo +"'";

                            ResultSet result;

                            //svolgo la delete
                            try {
                                state = con.createStatement();
                                state.execute(delete);

                            } catch (SQLException e1) {
                                // TODO Auto-generated catch block

                            }

                            String risultato = "";
                            //Mostro le modifiche
                            try {
                                result = state.executeQuery("select * from Ditta");
                                while (result.next()) {

                                    risultato += result.getString("Identificativo") + " ";
                                    risultato += result.getString("Recapito") + " ";
                                    risultato += result.getString("Fax") + " ";
                                    risultato += result.getString("Citta") + " ";
                                    risultato += result.getString("Via") + " ";
                                    risultato += result.getInt("NumeroCivico") + " ";
                                    risultato+=result.getString("CAP") + "\n";

                                }
                                area.setText(risultato);

                            } catch (SQLException e2) {

                            }

                        }

                    }

                    // creo ActionListener per Update

                    class updateActionListener implements ActionListener {

                        public void actionPerformed(ActionEvent e) {

                            // Faccio inserire dall'utente le informazioni necessarie per la modifica

                            String Identificativo = JOptionPane.showInputDialog("Inserisci Identificativo della ditta: ");
                            String Citta = JOptionPane.showInputDialog("Inserisci la città : ");
                            String CAP = JOptionPane.showInputDialog("Inserisci il CAP: ");

                            // Mi creo la stringa SQL
                            String update="UPDATE Ditta SET Citta ='" + Citta + "',CAP = '" +CAP + "'WHERE  Identificativo='" + Identificativo +"'";

                            // eseguo l'update
                            try {
                                state = con.createStatement();
                                state.executeUpdate(update);
                            } catch (SQLException e1) {
                                System.out.println("Errore update.");
                            }

                            // Mostro le informazioni delle ditte aggiornate
                            ResultSet result;
                            String risultato = "";
                            try {
                                result = state.executeQuery("select * from Ditta");
                                while (result.next()) {

                                    risultato += result.getString("Identificativo") + " ";
                                    risultato += result.getString("Recapito") + " ";
                                    risultato += result.getString("Fax") + " ";
                                    risultato += result.getString("Citta") + " ";
                                    risultato += result.getString("Via") + " ";
                                    risultato += result.getInt("NumeroCivico") + " ";
                                    risultato+=result.getString("CAP") + "\n";

                                }


                                area.setText(risultato);

                            } catch (SQLException e2) {
                                System.out.println("Errore");
                            }
                        }
                    }


                    // creo ActionLister per select
                    class selectActionListener implements ActionListener {

                        public void actionPerformed(ActionEvent e) {



                            String insert=JOptionPane.showInputDialog("Inserire la condizione: ");
                            String query=("SELECT * from Ditta WHERE " + insert);
                            //Creo la stringa per fare l'insert

                            //Svolgo la select
                            ResultSet result;
                            try
                            {
                                state = con.createStatement();
                                state.executeUpdate(query);

                            }
                            catch (SQLException e1)
                            {
                                e1.printStackTrace();
                            }
                            String risultato = "";
                            //Mostro le modifiche
                            try
                            {
                                result = state.executeQuery("select * from Ditta WHERE " + insert );
                                while (result.next())
                                {

                                    risultato += result.getString("Identificativo") + " ";
                                    risultato += result.getString("Recapito") + " ";
                                    risultato += result.getString("Fax") + " ";
                                    risultato += result.getString("Citta") + " ";
                                    risultato += result.getString("Via") + " ";
                                    risultato += result.getInt("NumeroCivico") + " ";
                                    risultato+=result.getString("CAP") + "\n";


                                }

                                area.setText(risultato);

                            }
                            catch (SQLException e2)
                            {
                                System.out.println("Errore");

                            }


                        }

                    }

                    // Creo ActionListener
                    ActionListener insertListener = new insertActionListener();
                    ActionListener deleteListener = new deleteActionListener();
                    ActionListener updateListener = new updateActionListener();
                    ActionListener selectListener= new selectActionListener();


                    // Associo gli ActionListener ai bottoni
                    insert.addActionListener(insertListener);
                    delete.addActionListener(deleteListener);
                    update.addActionListener(updateListener);
                    select.addActionListener(selectListener);

                    // creo il pannello

                    panel.add(insert);
                    panel.add(delete);
                    panel.add(update);
                    panel.add(select);
                    panel.add(area);


                    // aggiungo tutto nel frame
                    insertUpdateDelete.add(panel);

                } catch (Exception i) {

                    lblFarmacia.setForeground(Color.RED);
                    lblFarmacia.setFont(new Font("tahoma", Font.BOLD,33));
                    passwordField.setText("");
                    utente.setText("");

                }
            }
        });
        buttonLogin.setFont(new Font("Tahoma", Font.BOLD, 17));
        buttonLogin.setBounds(239, 238, 110, 33);
        frame.getContentPane().add(buttonLogin);

        lblFarmacia = new JLabel("Accesso Farmacia");
        lblFarmacia.setForeground(Color.BLACK);
        lblFarmacia.setFont(new Font("Book Antiqua", Font.BOLD, 33));
        lblFarmacia.setForeground(Color.GREEN);
        lblFarmacia.setBounds(176, 10, 401, 59);


        frame.getContentPane().add(lblFarmacia);

        JLabel marchio = new JLabel("");
        marchio.setIcon(new ImageIcon(""));
        marchio.setBounds(0, 0, 587, 340);
        frame.getContentPane().add(marchio);

    }
}