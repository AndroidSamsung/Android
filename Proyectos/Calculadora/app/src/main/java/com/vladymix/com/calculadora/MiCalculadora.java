package com.vladymix.com.calculadora;

/**
 * Created by Fabricio on 16/05/2015.
 */
public class MiCalculadora {
    private Double numero1, numero2,memoria,result;

    private String typeoperation;

    private boolean typedcomplete;
    private boolean typedmenory;

    private boolean operationcomplete;

    public MiCalculadora(){
        clearAllDates();
        operationcomplete = true;
    }

    public boolean isOperationcomplete() {
        return operationcomplete;
    }

    public void setOperationcomplete(boolean operationcomplete) {
        this.operationcomplete = operationcomplete;
    }

    public Double getNumero1() {
        return numero1;
    }

    public void setNumero1(Double numero1) {
        this.numero1 = numero1;
    }

    public Double getNumero2() {
        return numero2;
    }

    public void setNumero2(Double numero2) {
        this.numero2 = numero2;
    }

    public Double getMemoria() {
        return memoria;
    }

    public void setMemoria(Double memoria) {
        this.memoria = memoria;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }


    public String getTypeoperation() {
        return typeoperation;
    }

    public void setTypeoperation(String typeoperation) {
        this.typeoperation = typeoperation;
    }

    public boolean isTypedcomplete() {
        return typedcomplete;
    }

    public void setTypedcomplete(boolean typedcomplete) {
        this.typedcomplete = typedcomplete;
    }

    public boolean isTypedmenory() {
        return typedmenory;
    }

    public void setTypedmenory(boolean typedmenory) {
        this.typedmenory = typedmenory;
    }

    public void clearOperation(){
        typeoperation="";
    }

    public void clearAllDates(){
        memoria=numero1=numero2=result=0.0;
        typeoperation="";
    }

    public void makeOperation(){
        typedcomplete = true;
        setOperationcomplete(false);
        switch (typeoperation) {
            case "/":
                if (numero2 != 0.0) {
                    result = numero1 / numero2;
                    numero1=result;
                }
                break;
            case "x":
                if (numero2 != 0.0) {
                    result = numero1 * numero2;
                    numero1=result;
                }
                break;
            case "+":
                if (numero2 != 0.0) {
                    result = numero1 +numero2;
                    numero1=result;
                }
                break;
            case "-":
                if (numero2 != 0.0) {
                    result = numero1 - numero2;
                    numero1=result;
                }
                break;
            case "?":
                if (numero2 != 0.0) {
                    result = numero1 - numero2;
                    numero1=result;
                }
                break;
            case "=":
                onlyMakeOpetation();
                break;

            case "CA":
                clearAllDates();
                break;

            default:
                clearOperation();
                break;
        }

    }

    private void onlyMakeOpetation(){
        switch (typeoperation) {
            case "/":
                if (numero2 != 0.0) {
                    result = numero1 / numero2;
                    numero1=result;
                }
                break;
            case "x":
                if (numero2 != 0.0) {
                    result = numero1 * numero2;
                    numero1=result;
                }
                break;
            case "+":
                if (numero2 != 0.0) {
                    result = numero1 +numero2;
                    numero1=result;
                }
                break;
            case "-":
                if (numero2 != 0.0) {
                    result = numero1 - numero2;
                    numero1=result;
                }
                break;
        }
        //Borro todos los datos
        clearOperation();
        setOperationcomplete(true);
        typedcomplete = true;

    }

}
