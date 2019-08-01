package bss.intern.planb.View.AddAgenda;

import java.util.List;

import bss.intern.planb.Database.AgendaEvent;

public interface IView {
    void addAgendaEventToListInView(AgendaEvent agendaEvent);
    void successful();
}
