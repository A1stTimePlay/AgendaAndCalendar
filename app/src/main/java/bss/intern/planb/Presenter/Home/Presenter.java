package bss.intern.planb.Presenter.Home;

import java.util.Calendar;
import java.util.List;

import bss.intern.planb.Database.AgendaEvent;
import bss.intern.planb.Database.AgendaEventDao;
import bss.intern.planb.View.Home.IView;
import bss.intern.planb.WeekView.WeekViewEvent;

public class Presenter implements IPresenter {
    private IView view;
    private AgendaEventDao agendaEventDao;

    public Presenter(IView view, AgendaEventDao agendaEventDao) {
        this.view = view;
        this.agendaEventDao = agendaEventDao;
    }

    @Override
    public void loadAll() {
        List<AgendaEvent> agendaEventList = agendaEventDao.getAll();
        view.populateList(agendaEventList);
    }

    @Override
    public void quickCreate(AgendaEvent agendaEvent) {
        agendaEventDao.insertAgenda(agendaEvent);
    }

    @Override
    public void moveEvent(WeekViewEvent original, WeekViewEvent newly) {
        AgendaEvent temp = agendaEventDao.findById((int) original.getId());

        int dayDiffer = temp.getEndDay() - temp.getStartDay();
        int monthDiffer = temp.getEndMonth() - temp.getStartMonth();
        int yearDiffer = temp.getEndYear() - temp.getStartYear();
        int hourDiffer = temp.getEndHour() - temp.getStartHour();
        int minuteDiffer = temp.getEndMinute() - temp.getStartMinute();

        temp.setStartDay(newly.getStartTime().get(Calendar.DAY_OF_MONTH));
        temp.setStartMonth(newly.getStartTime().get(Calendar.MONTH));
        temp.setStartYear(newly.getStartTime().get(Calendar.YEAR));
        temp.setStartHour(newly.getStartTime().get(Calendar.HOUR));
        temp.setStartMinute(newly.getStartTime().get(Calendar.MINUTE));

        temp.setEndDay(newly.getStartTime().get(Calendar.DAY_OF_MONTH) + dayDiffer);
        temp.setEndMonth(newly.getStartTime().get(Calendar.MONTH) + monthDiffer);
        temp.setEndYear(newly.getStartTime().get(Calendar.YEAR) + yearDiffer);
        temp.setEndHour(newly.getStartTime().get(Calendar.HOUR) + hourDiffer);
        temp.setEndMinute(newly.getStartTime().get(Calendar.MINUTE) + minuteDiffer);

        agendaEventDao.updateAgenda(temp);
    }
}
