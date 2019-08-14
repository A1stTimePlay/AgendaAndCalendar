package bss.intern.planb.Presenter.Home;

import bss.intern.planb.Database.AgendaEvent;
import bss.intern.planb.WeekView.WeekViewEvent;

public interface IPresenter {
    void loadAll();
    void quickCreate(AgendaEvent agendaEvent);
    void moveEvent(WeekViewEvent original, WeekViewEvent newly);
}
