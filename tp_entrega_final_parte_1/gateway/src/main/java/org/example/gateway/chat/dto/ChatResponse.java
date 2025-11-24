package org.example.gateway.chat.dto;

public class ChatResponse {

    private String respuesta;
    private boolean exito;
    private String error;

    public ChatResponse() {}

    public ChatResponse(String respuesta, boolean exito) {
        this.respuesta = respuesta;
        this.exito = exito;
    }

    public static ChatResponse success(String respuesta) {
        return new ChatResponse(respuesta, true);
    }

    public static ChatResponse error(String error) {
        ChatResponse response = new ChatResponse();
        response.setExito(false);
        response.setError(error);
        return response;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
