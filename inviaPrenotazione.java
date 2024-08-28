private void inviaPrenotazione(String email, int[] idArray) throws IOException, OperationException {
    int id = bigliettiInAttesa.get(0).getIdCorsa();
    int numeroBagagli = 0;
    for(int i = 0; i < bigliettiInAttesa.size(); i++) {
        if(bigliettiInAttesa.get(i).getPresenzaBagaglio() == true) {
            numeroBagagli++;
        }
    }
    try {
        EntityCorsa ec = CorsaDAO.readCorsaId(id);
        EntityTratta et = TrattaDAO.readTratta(ec.getIdTratta());
        FileWriter fw = new FileWriter("./prenotazione.txt", false);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        pw.println("Copia prenotazione: ");
        if(ec.getTipo().compareTo("andata") == 0) {
            pw.println("Id corsa: " + ec.getIdCorsa() + "\n" + "Città di partenza: " + et.getCittàPartenza() + "\n" + "Città di arrivo: " + et.getCittàArrivo() + "\n" + "Data: " + ec.getData() + "\n" + "Orario di partenza: " + ec.getOrarioPartenza() + "\n" + "Orario di Arrivo: " + ec.getOrarioArrivo() + "\n" + "Numero Biglietti: " + bigliettiInAttesa.size() +  "\n" + "Numero Bagagli: " + numeroBagagli);
        }else {
            pw.println("Id corsa: " + ec.getIdCorsa() + "\n" + "Città di partenza: " + et.getCittàArrivo()+ "\n" + "Città di arrivo: " + et.getCittàPartenza() + "\n" + "Data: " + ec.getData() + "\n" + "Orario Partenza: " + ec.getOrarioPartenza() + "\n" + "Orario Arrivo: " + ec.getOrarioArrivo()+ "\n" +  "NumeroBiglietti: " + bigliettiInAttesa.size() + "\n" + "Numero Bagagli: " + numeroBagagli);
            
        }
        for(int i = 0; i < idArray.length; i++) {
            pw.println("Id biglietto[ " + i + " ]: " + idArray[i]);
        }
        pw.close();
        bw.close();
        fw.close();
        EmailSender.emailSender("./prenotazione.txt", email, "Prenotazione biglietti");
    } catch (DBConnectionException e) {
        throw new OperationException("Errore interno all'applicazione");
    } catch (DAOException e) {
        throw new OperationException("OPS, qualcosa è andato storto!");
    }
}
