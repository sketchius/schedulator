<template>
  <div>
    <h1>
      <button class="cal-button" @click="handleCalPrev()">&lt;</button>
      <div class="text">{{ getMonthLabel() }}</div>
      <button class="cal-button" @click="handleCalNext()">&gt;</button>
    </h1>
    <div class="row-layout">
      <div class="row sticky">
        <div class="cell header label"><div>Time</div></div>
        <div class="cell header">
          <div>{{ getDayLabel(0) }}</div>
        </div>
        <div class="cell header">
          <div>{{ getDayLabel(1) }}</div>
        </div>
        <div class="cell header">
          <div>{{ getDayLabel(2) }}</div>
        </div>
        <div class="cell header">
          <div>{{ getDayLabel(3) }}</div>
        </div>
        <div class="cell header">
          <div>{{ getDayLabel(4) }}</div>
        </div>
        <div class="cell header">
          <div>{{ getDayLabel(5) }}</div>
        </div>
        <div class="cell header">
          <div>{{ getDayLabel(6) }}</div>
        </div>
      </div>
      <div class="row" v-for="row in rows" v-bind:key="row">
        <div class="cell label">
          {{ row.time }}
        </div>
        <div class="cell" v-for="cell in row.cells" v-bind:key="cell">
          <div class="apt" v-if="cell.appointment">
            <div class="type">{{ cell.appointment.name }}</div>
            <div class="name">
              {{ cell.appointment.patientFirstName }}
              {{ cell.appointment.patientLastName }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import OfficeService from "../services/OfficeService";
import UserDetailsService from "../services/UserDetailsService";

import {
  startOfWeek,
  addDays,
  getDayOfYear,
  getHours,
  getMinutes,
  format,
  getWeekOfMonth,
  getMonth,
  getYear,
} from "date-fns";
import AptService from "../services/AptService";

export default {
  name: "calendar",
  data() {
    return {
      provider: {},
      office: {},
      appointments: {},
      rows: {},
      firstDayOfWeekDate: undefined,
      calDate: undefined,
    };
  },
  computed: {},
  methods: {
    getDayLabel(dayOfTheWeek) {
      if (this.firstDayOfWeekDate) {
        return format(addDays(this.firstDayOfWeekDate, dayOfTheWeek), "E do");
      }
      return "";
    },
    getMonthLabel() {
      if (this.calDate) {
        const weekOfMonth = getWeekOfMonth(this.calDate);
        return "Week " + weekOfMonth + " of " + format(this.calDate, "MMMM y");
      }
      return "";
    },
    buildCalendarByDate(dateInput) {
      let date = startOfWeek(new Date(dateInput));
      this.firstDayOfWeekDate = date;
      let openTime = this.convertTimeToInt(this.office.openTime);
      let closeTime = this.convertTimeToInt(this.office.closeTime);
      this.baseDate = date;
      const rows = [];
      for (let i = openTime; i <= closeTime; i++) {
        let newRow = {};
        newRow.time = this.getSlotAsString(i);
        newRow.cells = [];
        for (let j = 0; j < 7; j++) {
          const thisDate = addDays(date, j);
          const newCell = {};
          this.appointments.forEach((appointment) => {
            if (this.provider.id == appointment.detailsId) {
              const appointmentDate = new Date(appointment.date);

              if (
                getDayOfYear(appointmentDate) == getDayOfYear(thisDate) &&
                getHours(appointmentDate) - 4 == this.parseTimeSlotHours(i) &&
                getMinutes(appointmentDate) == this.parseTimeSlotMinutes(i)
              ) {
                newCell.appointment = appointment;
              }
            }
          });
          newRow.cells.push(newCell);
        }
        rows.push(newRow);
      }
      this.rows = rows;
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
    async setup() {
      UserDetailsService.getCurrent().then((response) => {
        this.provider = response.data;
        OfficeService.get(this.provider.officeId).then((response) => {
          this.office = response.data;
          this.calDate = new Date();

          let month = getMonth(this.calDate);
          let year = getYear(this.calDate);
          AptService.listByMonth(month+1, year).then((response) => {
            this.appointments = response.data;
            this.buildCalendarByDate(this.calDate);
          });
        });
      });
    },
    reloadAppointments() {
      this.appointments = {};
      this.rows = {};
      let month = getMonth(this.calDate);
      let year = getYear(this.calDate);
      AptService.listByMonth(month+1, year).then((response) => {
        this.appointments = response.data;
        this.buildCalendarByDate(this.calDate);
      });
    },
    handleCalPrev() {
      this.calDate = addDays(this.calDate, -7);
      this.reloadAppointments();
    },
    handleCalNext() {
      this.calDate = addDays(this.calDate, 7);
      this.reloadAppointments();
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
  },
  created() {
    this.setup();
  },
};
</script>

<style scoped>
.row-layout {
  display: flex;
  flex-direction: column;
}
.row {
  display: flex;
}
.row:nth-child(odd) {
  border-bottom: 2px solid var(--neutral400);
}
.row:nth-child(odd):has(.header) {
  border-bottom: none;
}
.sticky {
  position: sticky;
  top: 0px;
}
.label > div {
  font-size: 1.3rem;
  color: var(--neutral600);
}
.cell {
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 3.25rem;
  border: 1px solid var(--neutral400);
}
.cell:nth-child(even) {
  background: var(--neutral200);
}
.cell:has(.apt) {
  display: block;
}
.header {
  text-align: center;
  font-size: 1.3rem;
  height: 2rem;
  color: var(--neutral800);
  background: white;
  border: none;
  border-left: 1px solid var(--neutral400);
  border-right: 1px solid var(--neutral400);
  border-bottom: 4px solid var(--primary500);
}
.header:nth-child(even) {
  background: white;
}
.fixed {
  height: 100%;
  margin: 0;
  padding: 0;
  overflow-y: auto;
  height: 200px;
}
.borderhack {
  border: 2px solid var(--primary400);
  padding: 1rem 2rem;
}
th {
  display: none;
  position: sticky;
  top: 0px;
  padding: 0;
  font-weight: bold;
  font-size: 1.2rem;
  border: 2px solid var(--primary400);
  color: var(--primary600);
  background-color: white;
  text-align: left;
}
table {
  width: 100%;
  border-collapse: collapse;
}
td,
th {
  margin: 0;
}
td {
  padding: 0.5rem 2rem;
  font-size: 1.2rem;
  color: var(--primary600);
  border: 2px solid var(--primary400);
}
.content {
  overflow: hidden;
  padding: 0;
}
.apt {
  position: relative;
  overflow: hidden;
  height: 100%;
  padding: 0.5rem;
  margin: 0;
  background-color: var(--primary200);
  color: var(--primary200);
  border: 2px solid var(--primary600);
  box-sizing: border-box;
}
.apt .type {
  font-weight: bold;
  color: var(--primary800);
}
.apt .name {
  font-size: 0.9rem;
  color: var(--neutral800);
}
.label {
  border-right: 3px solid var(--primary500);
  border-left: none;
}
h1 {
  margin: 0;
  margin-bottom: 1rem;
  margin-left: 2rem;
  color: var(--primary600);
  display: flex;
  align-items: center;
  justify-content: center;
}
.text {
  display: inline-block;
  margin: 0 1rem;
}
.cal-button {
  display: inline-block;
  font-size: 1.2rem;
  padding: 0.25rem;
  color: var(--neutral500);
  border-radius: 3px;
  background: var(--neutral200);
  border: var(--neutral500) 1px solid;
}
</style>