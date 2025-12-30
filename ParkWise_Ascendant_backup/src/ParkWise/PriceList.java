package ParkWise;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PriceList {
    private int year;
    private Date effectiveDate;
    private double firstHourPrice;
    private double additionalHourPrice;
    private double fullDayPrice;

    public PriceList(int year, Date effectiveDate, double firstHour, double additionalHour, double fullDay) {
        this.year = year;
        this.effectiveDate = effectiveDate;
        this.firstHourPrice = firstHour;
        this.additionalHourPrice = additionalHour;
        this.fullDayPrice = fullDay;
    }

    // מימוש הפונקציה getPrices שמחזירה מפה של מחירים
    public Map<String, Double> getPrices() {
        Map<String, Double> prices = new HashMap<>();
        prices.put("First Hour", firstHourPrice);
        prices.put("Additional Hour", additionalHourPrice);
        prices.put("Full Day", fullDayPrice);
        return prices;
    }

    // Getters נוספים לשימוש כללי (אם צריך)
    public int getYear() { return year; }
    public Date getEffectiveDate() { return effectiveDate; }
}
