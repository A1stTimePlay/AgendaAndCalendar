package bss.intern.planb.View.AgendaDetail;

import bss.intern.planb.Database.AgendaEvent;

public interface IView {
    void displayAgendaEventDetail(AgendaEvent agendaEvent);
    void successfulDetele();
    void successfulEdit();
}
