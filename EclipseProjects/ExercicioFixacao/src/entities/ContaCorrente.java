package entities;

public class ContaCorrente {
    private int numeroConta;
    private int digitoVerificador1;
    private int digitoVerificador2;
    private int agencia;
    private int grauConta;
    private double saldo;
    private double limite;
    private boolean atividade;

    public ContaCorrente(int numeroConta, int agencia, int grauConta, double limite, double depositoInicial) {
        boolean erro = testeErro(grauConta,depositoInicial,limite);
        this.numeroConta = numeroConta;
        this.agencia = agencia;
        if (grauConta < 0 || grauConta > 3) {
            this.grauConta = 0; //defino como conta comum
        }
        else {
            this.grauConta = grauConta;
        }
        if (this.grauConta == 0 || limite < 0) {
            this.limite = 0;
        }
        else {
             this.limite = limite;
        }
        saldo = depositoInicial;
        atividade = !erro;
        if (!erro) {
           digitoVerificador1 = calc1DigV();
           digitoVerificador2 = calc2DigV();
        }
    }

    public int calc1DigV() {
        int k,auxNumConta=numeroConta, cont=1,soma=0,i=0,aux = 1;

        while (auxNumConta > 0) {
            cont=1;
            k=auxNumConta;
            i++;
            while (k >= 10) {
                k /= 10;
                cont *= 10;
            } //tenho o i-ésimo dígito
            soma += k * i;
            auxNumConta -= (cont * k);
        }
        while (soma>=10) {
            soma -= 9;
        }
        return  soma;
    }
    
    public int calc2DigV() {
    	int k, auxNumConta = numeroConta, soma = 0;
    	for (int i=1; auxNumConta > 0 ; i++) {
    		k = (auxNumConta / 10) * 10; //k é o valor dos primeiros dígitos exceto o último;
    		k = auxNumConta - k; //k é o último dígito;
    		soma += k * i;
    		auxNumConta /= 10;
    	}
    	
    	for ( ; soma >= 10; ) {
    		soma -= 9;
    	}
    	return soma;
    }
    
    public boolean lancOp(double valorOp, char tipoOp) {
    	if (tipoOp == 'C') {
			saldo += valorOp;
			return true;
    	}
    	else if (tipoOp == 'D') {
    		if (valorOp <= saldo + limite) {
    			saldo -= valorOp;
    			return true;
    		}
    		else {
    			System.out.println("Erro: Limite + saldo insuficientes.");
    			return false;
    		}
    	}
    	else{
    		System.out.println("Erro: Operação desconhecida rejeitada.");
    		return false;
    	}
    }
    
    public void setLimite ( double novoLimite) {
    	if (novoLimite > 0 && grauConta == 0){
    		System.out.println("Erro: conta comum deve ter limite zero.");
    	}
    	else if (novoLimite + saldo < 0) {
    		System.out.println("Erro: novo limite não cobre o saldo negativo da conta.");
    	}
    	else {
    		this.limite = novoLimite;
    	}
    }
    
    public void setGrau(int novoGrau) {
    	if(novoGrau < 0 || novoGrau > 3) {
    		System.out.println("Erro: grau inválido.");
    	}
    	else if(novoGrau == 0){
    		if(saldo >= 0){
    			this.limite = 0;
    			this.grauConta = novoGrau;
    		}
    		else {
    			System.out.println("Erro: conta com saldo negativo não pode ter grau 0.");
    		}
    	}
    	else {
    		this.grauConta = novoGrau;
    	}
    }
    
    public int getNumConta() {
    	return this.numeroConta;
    }
    
    public int getNumAgencia() {
    	return this.agencia;
    }
    
    public int getDigVer1() {
    	return this.digitoVerificador1;
    }
    
    public int getDigVer2() {
    	return this.digitoVerificador2;
    }
    
    public int getGrau() {
    	return this.grauConta;
    }
    
    public double getSaldo() {
    	return this.saldo;
    }
    
    public double getLimite() {
    	return this.limite;
    }
    
    public boolean isAtiva() {
    	return atividade;
    }
    
    private boolean testeErro (int grauConta, double depositoInicial,double limite) {
        boolean erro = false;
        if (grauConta > 3 || grauConta < 0){
            erro = true;
            System.out.println("Erro na criacao da conta em: Grau da Conta.\n" + "Valor esperado: entre 0 e 3.");
        }
        else{
            switch (grauConta){
                case 0:
                    if (depositoInicial < 100){
                        erro = true;
                    }
                    break;
                case 1:
                    if (depositoInicial < 200){
                        erro = true;
                    }
                    break;
                case 2:
                    if (depositoInicial < 500){
                        erro = true;
                    }
                    break;
                case 3:
                    if (depositoInicial < 1000){
                        erro = true;
                    }
                    break;
            }
            if (erro) {
                System.out.println("Erro no valor de deposito. Quantia insuficiente." );
            }
        }
        if(limite < 0) {
            System.out.println("Erro no valor do limite. Não pode ser quantia negativa.");
            erro = true;
        }
        if(limite > 0 && grauConta==0){
            System.out.println("Erro no valor do limite. Contas de grau 0 devem ter limite zero.");
            erro = true;
        }
        return erro;
    }
}
