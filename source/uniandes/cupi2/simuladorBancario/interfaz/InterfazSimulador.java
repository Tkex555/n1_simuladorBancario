/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogotï¿½ - Colombia)
 * Departamento de Ingenierï¿½a de Sistemas y Computaciï¿½n 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n1_simuladorBancario
 * Autor: Equipo Cupi2 2017
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */
package uniandes.cupi2.simuladorBancario.interfaz;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import uniandes.cupi2.simuladorBancario.mundo.*;

/**
 * Ventana principal de la aplicaciï¿½n.
 */
@SuppressWarnings("serial")
public class InterfazSimulador extends JFrame
{

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Clase principal del mundo.
     */
    private SimuladorBancario cuenta;

    // -----------------------------------------------------------------
    // Atributos de Interfaz
    // -----------------------------------------------------------------

    /**
     * Panel con la imagen.
     */
    private PanelImagen panelImagen;

    /**
     * Panel de botones para transacciones de cuentas de ahorro.
     */
    private PanelAhorros panelAhorros;

    /**
     * Panel de botones para transacciones de cuentas corriente.
     */
    private PanelCorriente panelCorriente;

    /**
     * Panel de botones para transacciones de CDT.
     */
    private PanelCDT panelCDT;

    /**
     * Panel de botones con las opciones de las transacciones.
     */
    private PanelOpciones panelOpciones;

    /**
     * Panel de visualizacion de datos de las cuentas.
     */
    private PanelSaldos panelSaldos;

    /**
     * Panel de visualizaciï¿½n de datos personales.
     */
    private PanelDatosCliente panelDatos;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Crea una nueva ventana principal del simulador de una cuenta bancaria. <br>
     * <b>post: </b> Se inicializï¿½ la interfaz principal y sus paneles.
     */
    public InterfazSimulador( )
    {
        setTitle( "Simulador bancario" );
        setSize( 600, 580 );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        cuenta = new SimuladorBancario( "1.041.976.808", "Juan David Castro Villarreal" );

        panelCDT = new PanelCDT( this );
        panelAhorros = new PanelAhorros( this );
        panelCorriente = new PanelCorriente( this );
        panelOpciones = new PanelOpciones( this );

        panelSaldos = new PanelSaldos( this );
        panelDatos = new PanelDatosCliente( );
        panelImagen = new PanelImagen( );

        // Organizar el panel principal
        getContentPane( ).setLayout( new BorderLayout( ) );

        JPanel panelCentral = new JPanel( );
        panelCentral.setLayout( new BorderLayout( ) );
        JPanel panelTransacciones = new JPanel( );
        panelTransacciones.setLayout( new GridLayout( 4, 1 ) );
        panelTransacciones.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Información Bancaria" ) ) );

        getContentPane( ).add( panelImagen, BorderLayout.NORTH );
        getContentPane( ).add( panelCentral, BorderLayout.CENTER );
        getContentPane( ).add( panelOpciones, BorderLayout.SOUTH );

        panelCentral.add( panelDatos, BorderLayout.NORTH );
        panelCentral.add( panelTransacciones, BorderLayout.CENTER );

        panelTransacciones.add( panelAhorros );
        panelTransacciones.add( panelCorriente );
        panelTransacciones.add( panelCDT );
        panelTransacciones.add( panelSaldos );

        setLocationRelativeTo( null );
        setResizable( false );

        actualizar( );
    }

    // -----------------------------------------------------------------
    // Mï¿½todos
    // -----------------------------------------------------------------

    /**
     * Actualiza la interfaz con los datos de la cuenta. <br>
     * <b>post: </b> Se actualizï¿½ los cambios en los paneles correspondientes.
     */
    public void actualizar( )
    {
        String nombre = cuenta.darNombre( );
        String cedula = cuenta.darCedula( );
        panelDatos.actualizarNombre( nombre );
        panelDatos.actualizarCedula( "" + cedula );
        panelSaldos.actualizarMes( cuenta.darMesActual( ) );
        panelSaldos.actualizarSaldoTotal( formatearValor( cuenta.calcularSaldoTotal( ) ) );

        panelCorriente.actualizarSaldoCorriente( formatearValor( cuenta.darCuentaCorriente( ).darSaldo( ) ) );
        panelAhorros.actualizarSaldoAhorros( formatearValor( cuenta.darCuentaAhorros( ).darSaldo( ) ) + "   [" + ( cuenta.darCuentaAhorros( ).darInteresMensual( ) * 100 ) + "%]" );
        panelCDT.actualizarSaldoCDT( formatearValor( cuenta.darCDT( ).calcularValorPresente( cuenta.darMesActual( ) ) ) + "   [" + ( cuenta.darCDT( ).darInteresMensual( ) * 100 ) + "%]" );

    }

    /**
     * Invierte un monto de dinero en un CDT. <br>
     * <b>post: </b> Se realizï¿½ la inversiï¿½n en un CDT.
     * @param pMonto Monto de dinero a invertir en el CDT.
     * @param pInteres Valor de los intereses en porcentaje.
     */
    public void invertirCDT( String pMonto, String pInteres )
    {
        if( pMonto != null )
        {
            try
            {
                int monto = Integer.parseInt( pMonto );
                double interes = Double.parseDouble( pInteres );
                if( monto > 0 && interes > 0 )
                {
                    cuenta.invertirCDT( monto, interes / 100 );
                    actualizar();
                }
                else
                {
                    JOptionPane.showMessageDialog( this, "Debe  ingresar valores positivos.", "Invertir en CDT", JOptionPane.ERROR_MESSAGE );
                }
            }
            catch( Exception e )
            {
                JOptionPane.showMessageDialog( this, "Se ingreso un monto de dinero o interï¿½s invalidolido.", "Invertir en CDT", JOptionPane.ERROR_MESSAGE );
            }
        }
    }

    /**
     * Cierra el CDT del cliente. <br>
     * <b>post: </b> Se cerrï¿½ el CDT del cliente.
     */
    public void cerrarCDT( )
    {
        cuenta.cerrarCDT( );
        actualizar();
    }

    /**
     * Retira un monto de dinero de la cuenta de ahorros del cliente. <br>
     * <b>post: </b> Se retirï¿½ un monto de dinero de la cuenta de ahorros del cliente.
     * @param pMonto Monto de dinero a retirar de la cuenta de ahorros del cliente.
     */
    public void retirarAhorros( String pMonto )
    {
        try
        {
            double monto = Double.parseDouble( pMonto );
            if( monto < 0 )
            {
                JOptionPane.showMessageDialog( this, "Debe ingresar un valor  positivo.", "Retirar", JOptionPane.ERROR_MESSAGE );
            }
            else if( monto > cuenta.darCuentaAhorros( ).darSaldo( ) )
            {
                JOptionPane.showMessageDialog( this, "No tiene saldo suficente.", "Retirar", JOptionPane.ERROR_MESSAGE );

            }
            else
            {
                cuenta.retirarCuentaAhorros( monto );
                actualizar( );
            }
        }
        catch( Exception e )
        {
            JOptionPane.showMessageDialog( this, "Se ingreso un monto de dinero invalido.", "Retirar", JOptionPane.ERROR_MESSAGE );

        }
    }

    /**
     * Consigna un monto de dinero en la cuenta de ahorros del cliente. <br>
     * <b>post: </b> Se consignï¿½ el monto de dinero a la cuenta de ahorros del cliente.
     * @param pMonto Monto de dinero a consignar en la cuenta de ahorros del cliente.
     */
    public void consignarAhorros( String pMonto )
    {
        try
        {
            double monto = Double.parseDouble( pMonto );

            if( monto >= 0 )
            {
                cuenta.consignarCuentaAhorros( Double.parseDouble( pMonto ) );
                actualizar();
            }
            else
            {
                JOptionPane.showMessageDialog( this, "Debe ingresar un valor positivo.", "Consignar", JOptionPane.ERROR_MESSAGE );
            }
        }
        catch( Exception e )
        {
            JOptionPane.showMessageDialog( this, "Se ingreso un monto de dinero invalido.", "Consignar", JOptionPane.ERROR_MESSAGE );

        }
    }

    /**
     * Retira un monto de dinero de la cuenta corriente. <br>
     * <b>post: </b> Se retirï¿½ de la cuenta corriente del cliente el monto especificado.
     * @param pMonto Monto de dinero a retirar de la cuenta corriente.
     */
    public void retirarCorriente( String pMonto )
    {
        try
        {
            double monto = Double.parseDouble( pMonto );
            if( monto < 0 )
            {
                JOptionPane.showMessageDialog( this, "Debe ingresar un valor positivo.", "Retirar", JOptionPane.ERROR_MESSAGE );
            }
            else if( monto > cuenta.darCuentaCorriente( ).darSaldo( ) )
            {
                JOptionPane.showMessageDialog( this, "No tiene saldo suficente.", "Retirar", JOptionPane.ERROR_MESSAGE );

            }
            else
            {
                cuenta.retirarCuentaCorriente( monto );
                actualizar( );
            }
        }
        catch( Exception e )
        {
            JOptionPane.showMessageDialog( this, "Se ingreso un monto de dinero invalido.", "Retirar", JOptionPane.ERROR_MESSAGE );
        }
    }

    /**
     * Consigna un monto de dinero en la cuenta corriente. <br>
     * <b>post: </b> Se consignï¿½ el monto especificado en la cuenta corriente del cliente.
     * @param pMonto Monto de dinero a consignar de la cuenta corriente.
     */
    public void consignarCorriente( String pMonto )
    {
        try
        {
            double monto = Double.parseDouble( pMonto );
            if( monto >= 0 )
            {
                cuenta.consignarCuentaCorriente( monto );
                actualizar( );
            }
            else
            {
                JOptionPane.showMessageDialog( this, "Debe ingresar un valor positivo.", "Consignar", JOptionPane.ERROR_MESSAGE );
            }
        }
        catch( Exception e )
        {
            JOptionPane.showMessageDialog( this, "Se ingreso un monto de dinero invalido.", "Consignar", JOptionPane.ERROR_MESSAGE );

        }
    }

    /**
     * Formatea un valor numï¿½rico para presentar en la interfaz.
     * @param pValor Valor numï¿½rico a ser formateado. pValor > 0.
     * @return Retorna un string con el valor formateado con puntos y signos.
     */
    private String formatearValor( double pValor )
    {
        DecimalFormat df = ( DecimalFormat )NumberFormat.getInstance( );
        df.applyPattern( "$ ###,###.##" );
        df.setMinimumFractionDigits( 2 );
        return df.format( pValor );
    }

    /**
     * Avanza un mes en la simulaciï¿½n del banco.
     */
    public void avanzarMesSimulacion( )
    {
        cuenta.avanzarMesSimulacion( );
        actualizar( );

    }

    // -----------------------------------------------------------------
    // Puntos de Extensiï¿½n
    // -----------------------------------------------------------------

    /**
     * Mï¿½todo para la extensiï¿½n 1.
     */
    public void reqFuncOpcion1( )
    {
        try {
            String strMesInicio = JOptionPane.showInputDialog(this, "Ingrese el mes de inicio del periodo:", "Saldo Promedio", JOptionPane.QUESTION_MESSAGE);
            String strMesFin = JOptionPane.showInputDialog(this, "Ingrese el mes de fin del periodo:", "Saldo Promedio", JOptionPane.QUESTION_MESSAGE);

            if (strMesInicio != null && strMesFin != null) {
                int mesInicio = Integer.parseInt(strMesInicio);
                int mesFin = Integer.parseInt(strMesFin);

                double saldoPromedio = cuenta.calcularSaldoPromedioEnPeriodo(mesInicio, mesFin);
                String mensaje = "El saldo promedio entre los meses " + mesInicio + " y " + mesFin + " es: " + formatearValor(saldoPromedio);
                JOptionPane.showMessageDialog(this, mensaje, "Saldo Promedio", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Debe ingresar valores numericos validos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Mï¿½todo para la extensiï¿½n 2.
     */
    public void reqFuncOpcion2( )
    {
        String respuesta = cuenta.metodo2( );
        actualizar( );
        JOptionPane.showMessageDialog( this, respuesta, "Respuesta.", JOptionPane.INFORMATION_MESSAGE );
    }

    // -----------------------------------------------------------------
    // Main
    // -----------------------------------------------------------------

    /**
     * Ejecuta la aplicación.
     * @param pArgs Parï¿½metros de la ejecuciï¿½n. No son necesarios.
     */
    public static void main( String[] pArgs )
    {
        try
        {
            // Unifica la interfaz para Mac y para Windows.
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );

            InterfazSimulador ventana = new InterfazSimulador( );
            ventana.setVisible( true );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }
}
