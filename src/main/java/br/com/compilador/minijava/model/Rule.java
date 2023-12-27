package br.com.compilador.minijava.model;


public class Rule {
    private String regularExpression;
    private String processing;
    private String tokenId;
    private String targetState;
    
    public Rule(String regularExpression, String processing, String tokenId, String targetState) {
        this.regularExpression = regularExpression;
        this.processing = processing;
        this.tokenId = tokenId;
        this.targetState = targetState;
    }
    
	public String getRegularExpression() {
		return regularExpression;
	}
	public void setRegularExpression(String regularExpression) {
		this.regularExpression = regularExpression;
	}
	public String getProcessing() {
		return processing;
	}
	public void setProcessing(String processing) {
		this.processing = processing;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getTargetState() {
		return targetState;
	}

	public void setTargetState(String targetState) {
		this.targetState = targetState;
	}



   
}