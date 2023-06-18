<template>
  <div class="container">
    <h1 class="aligned-row">
      <profile-pic
        class="picture"
        :providerId="providerId"
        :shape="'square'"
      />Dr. {{ provider.firstName }} {{ provider.lastName }}: Book an
      Appointment
    </h1>
    <div class="component-layout">
      <div class="day-picker">
        <h3>
          <div class="month-controls">
            <button class="cal-button" @click="handleCalPrev()">&lt;</button>
            <div class="month-text">{{ getMonthLabel() }}</div>
            <button class="cal-button" @click="handleCalNext()">&gt;</button>
          </div>
        </h3>
        <div class="row">
          <div class="header-container">
            <div class="cell header">Sunday</div>
          </div>
          <div class="header-container">
            <div class="cell header">Monday</div>
          </div>
          <div class="header-container">
            <div class="cell header">Tuesday</div>
          </div>
          <div class="header-container">
            <div class="cell header">Wednesday</div>
          </div>
          <div class="header-container">
            <div class="cell header">Thursday</div>
          </div>
          <div class="header-container">
            <div class="cell header">Friday</div>
          </div>
          <div class="header-container">
            <div class="cell header">Saturday</div>
          </div>
        </div>
        <div v-for="week in weeks" class="row" v-bind:key="week.id">
          <div v-for="day in week.days" v-bind:key="day.id">
            <div class="cell" @click="handleDateClick(day)">
              <div class="border">
                <div
                  :class="{
                    grey: day.grey,
                    closed: day.closed,
                    selected: day == selectedDay,
                  }"
                  class="content"
                >
                  <div class="text" :class="{ inactive: !day.dayOfTheMonth }">
                    {{ day.dayOfTheMonth }}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-if="timePickerVisible" class="time-picker">
        <h2>Available Times</h2>
        <div class="time-picker-times">
          <div
            :class="{ selected: slot == selectedTime }"
            @click="handleTimeClick(slot)"
            v-for="slot in selectedDay.slots"
            :key="slot"
            class="time-card"
          >
            {{ getSlotAsString(slot) }}
          </div>
        </div>
      </div>
    </div>
    <div class="button-container">
      <button v-if="selectedTime" @click="handleConfirm()">Confirm</button>
    </div>
  </div>
</template>

<script>
import {
  format,
  setDate,
  getDay,
  setHours,
  setMinutes,
  getDaysInMonth,
  getDayOfYear,
  getHours,
  getMinutes,
  isBefore,
  isAfter,
  addHours,
  addDays,
  addMonths,
  getMonth,
  getYear,
} from "date-fns";
import AvailabilityService from "../services/AvailabilityService";
import UserDetailsService from "../services/UserDetailsService";
import OfficeService from "../services/OfficeService";
import AptService from "../services/AptService";
import ProfilePic from "../components/ProfilePic.vue";
import CalendarService from "../services/CalendarService";

export default {
  name: "date-time-picker",
  props: ["providerId"],
  data() {
    return {
      office: {},
      provider: {},
      weeks: [],
      appointmentsInMonth: [],
      selectedDay: undefined,
      selectedTime: undefined,
      baseDate: {},
      calDate: {},
      nationalHolidays: "none",
    };
  },
  components: {
    ProfilePic,
  },
  computed: {
    timePickerVisible() {
      return this.selectedDay && this.selectedDay.slots;
    },
    profileId() {
      return this.provider.id;
    },
  },
  methods: {
    getMonthLabel() {
      if (this.calDate) {
        return format(this.calDate, "MMMM y");
      }
      return "";
    },
    buildCalendarByDate(currentAppointments, provider, office, availability) {
      let availableFromDate = new Date(availability.availableFrom);
      let availableToDate = new Date(availability.availableTo);
      let date = this.calDate;
      this.baseDate = date;
      date = setDate(date, 1);
      const weeks = [];
      const dayOfTheWeekOfTheFirst = getDay(date);
      const lastDayOfTheMonth = getDaysInMonth(date);
      let dayCount = 1;
      let cellCount = 0;
      for (let i = 0; i < 6; i++) {
        const newWeek = {};
        newWeek.days = [];
        for (let j = 0; j < 7; j++) {
          const newDay = {};
          if (
            cellCount >= dayOfTheWeekOfTheFirst &&
            dayCount <= lastDayOfTheMonth
          ) {
            newDay.dayOfTheMonth = dayCount;
            newDay.date = setDate(new Date(this.calDate), dayCount);
            dayCount++;
            let closed = false;
            let unavailable = false;

            for (let k = 0; k < this.nationalHolidays.length; k++) {
              let holidayDate = new Date(this.nationalHolidays[k].date);
              holidayDate = addHours(holidayDate, 12);
              if (getDayOfYear(holidayDate) == getDayOfYear(newDay.date)) {
                closed = true;
                newDay.closed = true;
              }
            }


            if (
              isBefore(newDay.date, availableFromDate) ||
              isAfter(newDay.date, availableToDate)
            ) {
              unavailable = true;
            }

            if (j == 0 || j == 6) {
              closed == true;
              newDay.closed = true;
            }

            if (
              isAfter(newDay.date, addDays(new Date(), -1)) &&
              !closed &&
              !unavailable
            ) {
              newDay.slots = this.getAvailableSlotsForDate(
                newDay.date,
                currentAppointments,
                provider,
                office
              );
            } else {
              newDay.slots = [];
            }
            if (newDay.slots.length == 0 || unavailable) {
              newDay.grey = "grey";
            }

            console.log("newDay.grey = " + newDay.grey);
          }
          cellCount++;
          newWeek.days.push(newDay);
        }
        weeks.push(newWeek);
      }
      this.weeks = weeks;
    },
    getAvailableSlotsForDate(thisDate, currentAppointments, provider) {
      const timeSlots = [];

      let openTime = this.convertTimeToInt(this.office.openTime);
      let closeTime = this.convertTimeToInt(this.office.closeTime);
      for (let i = 0; i < 48; i++) {
        if (i >= openTime && i <= closeTime) {
          let slotAvailable = true;

          for (let j = 0; j < currentAppointments.length; j++) {
            let appointment = currentAppointments[j];
            if (provider.id == appointment.detailsId) {
              const appointmentDate = new Date(appointment.date);

              if (
                getDayOfYear(appointmentDate) == getDayOfYear(thisDate) &&
                getHours(appointmentDate) - 4 == this.parseTimeSlotHours(i) &&
                getMinutes(appointmentDate) == this.parseTimeSlotMinutes(i)
              ) {
                slotAvailable = false;
                break;
              }
            }
          }
          if (slotAvailable) timeSlots.push(i);
        }
      }

      return timeSlots;
    },
    getSlotAsString(slot) {
      slot = slot / 2;
      let abbreviation = "AM";
      if (slot >= 12) {
        abbreviation = "PM";
      }
      if (slot >= 13) {
        slot = slot - 12;
      }

      if (slot % 1 == 0.5) {
        slot = slot - 0.5;
        return slot + ":30 " + abbreviation;
      } else {
        return slot + ":00 " + abbreviation;
      }
    },
    convertTimeToInt(timeString) {
      const splitString = timeString.split(":");
      let time = Number(splitString[0]);
      if (timeString.includes("PM") && !splitString[0] != "12") {
        time = time + 12;
      }
      time = time * 2;
      if (!splitString[1].includes("00")) time = time + 1;
      return time;
    },
    parseTimeSlotHours(timeSlot) {
      timeSlot = timeSlot / 2;
      if (timeSlot % 1 == 0.5) timeSlot = timeSlot - 0.5;
      return timeSlot;
    },
    parseTimeSlotMinutes(timeSlot) {
      timeSlot = timeSlot / 2;
      if (timeSlot % 1 == 0.5) return 30;
      else return 0;
    },
    getDateTime() {
      let dateTime = this.baseDate;
      dateTime = setDate(dateTime, this.selectedDay.dayOfTheMonth);
      dateTime = setHours(dateTime, this.parseTimeSlotHours(this.selectedTime));
      dateTime = setMinutes(
        dateTime,
        this.parseTimeSlotMinutes(this.selectedTime)
      );
      return dateTime;
    },
    handleDateClick(day) {
      this.selectedTime = undefined;
      if (day.dayOfTheMonth && !day.grey && !day.closed) {
        this.selectedDay = day;
      } else {
        this.selectedDay = undefined;
      }
    },
    handleTimeClick(time) {
      this.selectedTime = time;
    },
    handleConfirm() {
      this.$emit("submitDateTime", { message: this.getDateTime() });
    },

    handleCalPrev() {
      this.calDate = addMonths(this.calDate, -1);
      this.reloadCalendar();
    },
    handleCalNext() {
      this.calDate = addMonths(this.calDate, 1);
      this.reloadCalendar();
    },
    reloadCalendar() {
      let month = getMonth(this.calDate);
      let year = getYear(this.calDate);
      this.weeks = [];

      AvailabilityService.getByDetails(this.provider.id).then(
        function (response) {
          let availability = response.data;
          AptService.listByMonth(month + 1, year).then(
            function (response) {
              this.buildCalendarByDate(
                response.data,
                this.provider,
                this.office,
                availability
              );
            }.bind(this)
          );
        }.bind(this)
      );
    },
    setup() {
      CalendarService.getNationalHolidays().then(
        function (response) {
          this.nationalHolidays = response.data;
          UserDetailsService.get(this.providerId).then((response) => {
            this.provider = response.data;
            const provider = response.data;

            OfficeService.get(this.provider.officeId).then((response) => {
              this.office = response.data;
              const office = response.data;

              this.calDate = new Date();

              let month = getMonth(this.calDate);
              let year = getYear(this.calDate);
              AvailabilityService.getByDetails(provider.id).then(
                function (response) {
                  let availability = response.data;
                  AptService.listByMonth(month + 1, year).then(
                    function (response) {
                      this.buildCalendarByDate(
                        response.data,
                        provider,
                        office,
                        availability
                      );
                    }.bind(this)
                  );
                }.bind(this)
              );
            });
          });
        }.bind(this)
      );
    },
  },
  created() {
    this.setup();
  },
};
</script>

<style scoped>
.container {
  transition: all 0.5s;
  margin-top: 3rem;
  margin-right: 2rem;
  position: relative;
  padding: 3rem 2rem;
  padding-top: 0;
  border: var(--primary500) 3px solid;
  border-radius: 0.5rem;
  height: 550px;
}
.container:has(button) {
  height: 630px;
}
.component-layout {
  display: grid;
  grid-template-columns: 2fr 25rem;
}
h1 {
  display: inline-block;
  padding: 0 1rem;
  position: relative;
  top: -1.9rem;
  background: white;
  width: fit-content;
  margin: 0;
  font-size: 2rem;
  color: var(--primary500);
}
.dr-name {
  display: inline-block;
  padding: 0 1rem;
  margin: 0;
  font-size: 1.8rem;
  color: var(--primary800);
}
.day-picker {
  position: relative;
  padding-top: 0.5rem;
  display: flex;
  flex-direction: column;
}
.time-picker {
  padding: 0 2rem;
}
.time-picker-times {
  height: 27rem;
  width: 20rem;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
}
.row {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  height: 5rem;
}
.cell {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 5rem;
}
.row:has(.header-container) {
  height: 1.6rem;
}
.header {
  height: 1.6rem;
  font-size: 1.3rem;
  background-color: var(--primary600);
  color: var(--neutral200);
  border: 1px solid var(--neutral200);
}

div:first-child > .header {
  border-bottom-left-radius: 1rem;
  border-top-left-radius: 1rem;
}
div:last-child > .header {
  border-bottom-right-radius: 1rem;
  border-top-right-radius: 1rem;
}
.border {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 85%;
  height: 70%;
  border-radius: 1rem;
  box-shadow: 8px 8px var(--primary200);
  border: 3px solid var(--neutral400);
}
.border:has(.grey) {
  box-shadow: none;
  border: 3px solid var(--neutral200);
}
.border:has(.closed) {
  box-shadow: none;
  border: 3px solid #f3b7ce;
}
.border:has(.inactive) {
  box-shadow: none;

  border: none;
}
.border:has(.selected) {
  background-color: var(--primary400);
  color: white;
  border-color: var(--primary600);
}
.content {
  color: var(--primary600);
  font-size: 2rem;
  font-weight: 500;
}
.grey {
  font-size: 1.5rem;
  color: var(--neutral400);
  font-weight: 400;
}
.closed {
  font-size: 1.5rem;
  color: #b48195;
  font-weight: 400;
}
.content.selected {
  color: white;
}
.text {
  display: inline-block;
}
h2 {
  font-size: 2rem;
  color: var(--primary600);
  margin: 0;
  margin-bottom: 1rem;
  margin-left: 1rem;
}
.time-card {
  margin-top: 0.5rem;
  margin-bottom: 0.5rem;
  background: var(--neutral200);
  font-size: 1.5rem;
  color: var(--neutral600);
  border-radius: 2rem;
  width: 8ch;
  text-align: center;
}
.time-card.selected {
  background-color: var(--primary400);
  color: white;
}
.button-container {
  position: relative;
  display: flex;
  justify-content: flex-end;
}
button {
  display: block;
  position: relative;
  top: -2rem;
  margin-right: 2rem;
  width: 20rem;
  padding: 0.5rem 1.5rem;
  font-size: 1.5rem;
  border: 3px solid var(--primary600);
  color: white;
  border-radius: 1rem;
  background-color: var(--primary600);
}
.picture {
  margin-right: 1rem;
}
.aligned-row {
  display: flex;
  align-items: center;
}

.month-text {
  display: inline-block;
  margin: 0 1rem;
  width: 14ch;
  display: flex;
  align-items: center;
  justify-content: center;
}
.month-controls {
  background: var(--neutral200);
  width: fit-content;
  display: flex;
  padding: 0.5rem 2rem;
  border-radius: 1rem;
}
h3 {
  margin: 0;
  margin-bottom: 0.5rem;
  position: relative;
  font-size: 2rem;
  color: var(--primary600);
  display: flex;
  align-items: center;
  justify-content: center;
}
.cal-button {
  position: static;
  margin: 0;
  width: fit-content;
  display: inline-block;
  font-size: 1.2rem;
  padding: 0.25rem;
  color: var(--neutral500);
  border-radius: 3px;
  background: white;
  border: var(--neutral500) 1px solid;
}
</style>