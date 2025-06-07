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
package uniandes.cupi2.simuladorBancario.mundo;

/**
 * Clase que representa el simulador bancario para las tres cuentas de un cliente.
 */
public class SimuladorBancario
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Cedula del cliente.
     */
    private String cedula;

    /**
     * Nombre del cliente.
     */
    private String nombre;

    /**
     * Mes actual.
     */
    private int mesActual;

    /**
     * Cuenta corriente del cliente.
     */
    private CuentaCorriente corriente;

    /**
     * Cuenta de ahorros del cliente.
     */
    private CuentaAhorros ahorros;

    /**
     * CDT del cliente.
     */
    private CDT inversion;

    // -----------------------------------------------------------------
    // Mï¿½todos
    // -----------------------------------------------------------------

    /**
     * Inicializa el simulador con la informaciï¿½n del cliente. <br>
     * <b>post: </b> El mes fue inicializado en 1, y las tres cuentas (CDT, corriente y de ahorros) fueron inicializadas como vacï¿½as. <br>
     * @param pCedula Cï¿½dula del nuevo cliente. pCedula != null && pCedula != "".
     * @param pNombre Nombre del nuevo cliente. pNombre != null && pNombre != "".
     */
    public SimuladorBancario( String pCedula, String pNombre )
    {
        // Inicializa los atributos personales del cliente
        nombre = pNombre;
        cedula = pCedula;
        // Inicializa el mes en el 1
        mesActual = 1;
        // Inicializa las tres cuentas en vacï¿½o
        corriente = new CuentaCorriente( );
        ahorros = new CuentaAhorros( );
        inversion = new CDT( );
    }

    /**
     * Retorna el nombre del cliente.
     * @return Nombre del cliente.
     */
    public String darNombre( )
    {
        return nombre;
    }

    /**
     * Retorna la cï¿½dula del cliente.
     * @return Cï¿½dula del cliente.
     */
    public String darCedula( )
    {
        return cedula;
    }

    /**
     * Retorna la cuenta corriente del cliente.
     * @return Cuenta corriente del cliente.
     */
    public CuentaCorriente darCuentaCorriente( )
    {
        return corriente;
    }

    /**
     * Retorna la cuenta de ahorros del cliente.
     * @return Cuenta de ahorros del cliente.
     */
    public CuentaAhorros darCuentaAhorros( )
    {
        return ahorros;
    }

    /**
     * Retorna el CDT del cliente.
     * @return CDT del cliente.
     */
    public CDT darCDT( )
    {
        return inversion;
    }

    /**
     * Retorna el mes en el que se encuentra la simulaciï¿½n.
     * @return Mes actual.
     */
    public int darMesActual( )
    {
        return mesActual;
    }

    /**
     * Calcula el saldo total de las cuentas del cliente.
     * @return Saldo total de las cuentas del cliente.
     */
    public double calcularSaldoTotal( )
    {
        return corriente.darSaldo( ) + ahorros.darSaldo( ) + inversion.calcularValorPresente( mesActual );
    }

    /**
     * Invierte un monto de dinero en un CDT. <br>
     * <b>post: </b> Invirtiï¿½ un monto de dinero en un CDT.
     * @param pMonto Monto de dinero a invertir en un CDT. pMonto > 0.
     * @param pInteresMensual Interï¿½s del CDT elegido por el cliente. pInteresMensual > 0.
     */
    public void invertirCDT( double pMonto, double pInteresMensual )
    {
        inversion.invertir( pMonto, pInteresMensual, mesActual );
    }

    /**
     * Consigna un monto de dinero en la cuenta corriente. <br>
     * <b>post: </b> Consignï¿½ un monto de dinero en la cuenta corriente.
     * @param pMonto Monto de dinero a consignar en la cuenta. pMonto > 0.
     */
    public void consignarCuentaCorriente( double pMonto )
    {
        corriente.consignarMonto( pMonto );
    }

    /**
     * Consigna un monto de dinero en la cuenta de ahorros. <br>
     * * <b>post: </b> Consignï¿½ un monto de dinero en la cuenta de ahorros.
     * @param pMonto Monto de dinero a consignar en la cuenta. pMonto > 0.
     */
    public void consignarCuentaAhorros( double pMonto )
    {
        ahorros.consignarMonto( pMonto );
    }

    /**
     * Retira un monto de dinero de la cuenta corriente. <br>
     * <b>post: </b> Se redujo el saldo de la cuenta en el monto especificado.
     * @param pMonto Monto de dinero a retirar de la cuenta. pMonto > 0.
     */
    public void retirarCuentaCorriente( double pMonto )
    {
        corriente.retirarMonto( pMonto );
    }

    /**
     * Retira un monto de dinero de la cuenta de ahorros. <br>
     * <b>post: </b> Se redujo el saldo de la cuenta en el monto especificado.
     * @param pMonto Monto de dinero a retirar de la cuenta. pMonto > 0.
     */
    public void retirarCuentaAhorros( double pMonto )
    {
        ahorros.retirarMonto( pMonto );
    }

    /**
     * Avanza en un mes la simulaciï¿½n. <br>
     * <b>post: </b> Se avanzï¿½ el mes de la simulaciï¿½n en 1. Se actualizï¿½ el saldo de la cuenta de ahorros.
     */
    public void avanzarMesSimulacion( )
    {
        mesActual += 1;
        ahorros.actualizarSaldoPorPasoMes( );
    }

    /**
     * Cierra el CDT, pasando el saldo a la cuenta corriente. <br>
     * <b>pre: </b> La cuenta corriente y el CDT han sido inicializados. <br>
     * <b>post: </b> El CDT quedï¿½ cerrado y con valores en 0, y la cuenta corriente aumentï¿½ su saldo en el valor del cierre del CDT.
     */
    public void cerrarCDT( )
    {
        double valorCierreCDT = inversion.cerrar( mesActual );
        corriente.consignarMonto( valorCierreCDT );
    }

    /**
     * Retorna el resultado de la extensiï¿½n 1.
     * @return Respuesta 1.
     */
    public String metodo1( )
    {
        return "Respuesta 1";
    }

    /**
     * Retorna el resultado de la extensiï¿½n 2.
     * @return Respuesta 2.
     */
    public String metodo2( )
    {
        return "Respuesta 2";
    }

    /**
     * Calcula el saldo promedio de las cuentas del cliente.
     * @return Saldo promedio de las cuentas del cliente.
     */
    public double calcularSaldoPromedio( )
    {
        double saldoTotal = calcularSaldoTotal( );
        if ( mesActual > 0 )
        {
            return saldoTotal / mesActual;
        }
        return 0;
    }

    /**
     * Calcula el saldo promedio de las cuentas del cliente en un perÃ­odo determinado.
     * @param mesInicio Mes de inicio del perÃ­odo. mesInicio > 0.
     * @param mesFin Mes de fin del perÃ­odo. mesFin >= mesInicio.
     * @return Saldo promedio de las cuentas del cliente en el perÃ­odo.
     */
    public double calcularSaldoPromedioEnPeriodo(int mesInicio, int mesFin) {
        if (mesInicio <= 0 || mesFin < mesInicio) {
            throw new IllegalArgumentException("El período especificado no es válido.");
        }

        double saldoTotal = 0;
        int meses = mesFin - mesInicio + 1;

        // Guardar estado original para restaurar al final
        int mesOriginal = mesActual;
        double saldoAhorrosTemp = ahorros.darSaldo();

        for (int mes = 1; mes < mesInicio; mes++) {
            // Simular actualización de saldo de ahorros al avanzar meses previos
            saldoAhorrosTemp *= (1 + ahorros.darInteresMensual());
        }

        for (int mes = mesInicio; mes <= mesFin; mes++) {
            mesActual = mes; // Cambiar mes para CDT y corriente

            if (mes > mesInicio) {
                // Actualizar saldo de ahorros con interés
                saldoAhorrosTemp *= (1 + ahorros.darInteresMensual());
            }

            double valorCDT = inversion.calcularValorPresente(mesActual);
            double saldoCorrienteActual = corriente.darSaldo();

            saldoTotal += saldoAhorrosTemp + saldoCorrienteActual + valorCDT;
        }

        // Restaurar estado original
        mesActual = mesOriginal;

        return saldoTotal / meses;
    }



}