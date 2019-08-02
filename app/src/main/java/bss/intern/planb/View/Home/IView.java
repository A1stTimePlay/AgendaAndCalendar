package bss.intern.planb.View.Home;

import java.util.List;

import bss.intern.planb.Database.AgendaEvent;

public interface IView {
    void populateList(List<AgendaEvent> agendaEventList);
}
