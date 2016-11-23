package com.github.kyriosdata.oes;

import org.junit.jupiter.api.Test;
import org.openehr.rm.datatypes.basic.DvBoolean;
import org.openehr.rm.datatypes.basic.DvIdentifier;
import org.openehr.rm.datatypes.text.CodePhrase;
import org.openehr.rm.datatypes.uri.DvEHRURI;
import org.openehr.rm.datatypes.uri.DvURI;
import org.openehr.rm.support.identification.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AdaptadorTest {

    @Test
    public void dvBoolean() {
        // Um objeto a ser adaptado
        DvBoolean dv = new DvBoolean(false);

        // Converte
        byte[] seed = new Adaptador().adapta(dv);

        // Adaptador
        Adaptador a = new Adaptador(seed);

        // Define registro de interesse
        a.setInicioRegistro(0);

        // Restaura
        DvBoolean recuperado = a.oeDvBoolean(0);

        // Verifica
        assertFalse(recuperado.getValue());
    }

    @Test
    public void dvIdentifier() {

        // Um objeto a ser adaptado
        DvIdentifier dv = new DvIdentifier("i", "a", "id", "type");

        // Converte
        byte[] seed = new Adaptador().adapta(dv);

        // Recuperação
        Adaptador a = new Adaptador(seed);

        // Restaura
        DvIdentifier recuperado = a.oeDvIdentifier(0);

        // Verifica
        assertEquals("i", recuperado.getIssuer());
        assertEquals("a", recuperado.getAssigner());
        assertEquals("id", recuperado.getId());
        assertEquals("type", recuperado.getType());
    }

    @Test
    public void genericId() {
        Adaptador a = new Adaptador();

        GenericID v = new GenericID("value", "scheme");

        byte[] seed = a.adapta(v);

        GenericID recuperado = a.oeGenericID(seed);

        assertEquals("value", recuperado.getValue());
        assertEquals("scheme", recuperado.getScheme());
    }

    @Test
    public void internetId() {
        Adaptador a = new Adaptador();

        // Um objeto a ser adaptado
        InternetID v = new InternetID("id");

        // Converte
        byte[] bytes = a.adapta(v);

        // Restaura
        InternetID recuperado = a.oeInternetID(bytes);

        // Verifica
        assertEquals("id", recuperado.getValue());
    }

    @Test
    public void isoOid() {
        Adaptador a = new Adaptador();

        String linuxLoadOID = "1.3.6.1.4.1.2021.10.1.3.1";
        ISO_OID v = new ISO_OID(linuxLoadOID);

        byte[] bytes = a.adapta(v);

        ISO_OID recuperado = a.oeISO_OID(bytes);

        assertEquals(linuxLoadOID, recuperado.getValue());
    }

    @Test
    public void templateId() {
        TemplateID v = new TemplateID("templateId");

        byte[] bytes = new Adaptador().adapta(v);

        Adaptador a = new Adaptador(bytes);

        TemplateID recuperado = a.oeTemplateID();

        assertEquals("templateId", recuperado.getValue());
    }

    @Test
    public void terminologyId() {

        TerminologyID v = new TerminologyID("id(v)");

        byte[] bytes = new Adaptador().adapta(v);

        Adaptador a = new Adaptador(bytes);

        TerminologyID recuperado = a.oeTerminologyID(bytes);

        assertEquals("id(v)", recuperado.getValue());
    }

    @Test
    public void uuid() {
        Adaptador a = new Adaptador();

        String guid = java.util.UUID.randomUUID().toString();

        UUID v = new UUID(guid);

        byte[] bytes = a.adapta(v);

        UUID recuperado = a.oeUUID(bytes);

        assertEquals(guid, recuperado.getValue());
    }

    @Test
    public void codePhrase() {
        TerminologyID tid = new TerminologyID("id(v)");
        CodePhrase cp = new CodePhrase(tid, "codigo");

        byte[] bytes = new Adaptador().adapta(cp);

        Adaptador a = new Adaptador(bytes);

        CodePhrase recuperado = a.oeCodePhrase(bytes);

        assertEquals(tid, recuperado.getTerminologyId());
        assertEquals("codigo", recuperado.getCodeString());
    }

    @Test
    public void dvURI() {
        Adaptador a = new Adaptador();

        String uri = "mailto:fabio@inf.ufg.br";

        DvURI duri = new DvURI(uri);
        byte[] bytes = a.adapta(duri);

        DvURI recuperado = a.oeDvURI(bytes);

        assertEquals(uri, recuperado.getValue());
    }

    @Test
    public void dvEHRURI() {
        Adaptador a = new Adaptador();

        String uri = "ehr:fabio@inf.ufg.br";

        DvEHRURI duri = new DvEHRURI(uri);
        byte[] bytes = a.adapta(duri);

        DvEHRURI recuperado = a.oeDvEHRURI(bytes);

        assertEquals(uri, recuperado.getValue());
    }

    @Test
    public void versionTreeID() {
        Adaptador a = new Adaptador();

        VersionTreeID v = new VersionTreeID(1, 2, 3);
        byte[] bytes = a.adapta(v);

        VersionTreeID recuperado = a.oeVersionTreeID(bytes);

        assertEquals("1.2.3", recuperado.getValue());
    }

    @Test
    public void partyRef() {
        partyRefBase(new TemplateID("TemplateID"));
        partyRefBase(new TerminologyID("TemplateID"));
    }

    private void partyRefBase(ObjectID objectId) {
        PartyRef pr = new PartyRef(objectId, "tipo");

        // Serializa PartyRef
        byte[] seed = new Adaptador().adapta(pr);

        // Desserializa
        Adaptador a = new Adaptador(seed);
        PartyRef r = a.oePartyRef();

        assertEquals(pr, r);
    }

}
