package bss.intern.planb.View.ShowOnMap;

import java.util.List;

import bss.intern.planb.Database.AgendaEvent;

public interface IView {
    void loadPlaceList(List<AgendaEvent> agendaEventList);
}
