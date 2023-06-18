
import axios from 'axios';

export default {

  getNationalHolidays() {
    return axios.get('https://date.nager.at/api/v3/PublicHolidays/2023/us')
  }
}
