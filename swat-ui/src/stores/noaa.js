import { defineStore } from "pinia";
import http from "@/plugins/axios";

export const useNOAAStore = defineStore("noaa", {
  state: () => ({
    currCon: {},
    currKpi: {},
    currSlrWnd: {},
    currSlrPrb: {},
    recentAlert: {},
    imgURLs: [
      "https://services.swpc.noaa.gov/images/animations/suvi/primary/171/latest.png",
      "https://services.swpc.noaa.gov/images/animations/sdo-hmii/latest.jpg",
      "https://services.swpc.noaa.gov/images/animations/suvi/primary/284/latest.png",
    ],
  }),

  getters: {
    getCurrCon(state) {
      return state.currCon;
    },
    getCurrKpi(state) {
      return state.currKpi;
    },
    getCurrSlrWnd(state) {
      return state.currSlrWnd;
    },
    getCurrSlrPrb(state) {
      return state.currSlrPrb;
    },
    getRecentAlert(state) {
      return state.recentAlert;
    },
  },

  actions: {
    async fetchData(token) {
      await this.fetchCurrCon(token);
      await this.fetchCurrKpi(token);
      await this.fetchCurrSlrWind(token);
      await this.fetchSlrPrb(token);
      await this.fetchRecentAlert(token);
    },

    async fetchCurrCon(token) {
      try {
        const data = await http.get("/noaa/curr-con", {
          headers: {
            Authorization: "Bearer " + token,
          },
        });

        this.currCon = data.data;
      } catch (error) {
        console.log(error);
      }
    },

    async fetchCurrKpi(token) {
      try {
        const data = await http.get("/noaa/curr-kpi", {
          headers: {
            Authorization: "Bearer " + token,
          },
        });

        this.currKpi = data.data;
      } catch (error) {
        console.log(error);
      }
    },

    async fetchCurrSlrWind(token) {
      try {
        const data = await http.get("/noaa/curr-slr-wind", {
          headers: {
            Authorization: "Bearer " + token,
          },
        });

        this.currSlrWnd = data.data;
      } catch (error) {
        console.log(error);
      }
    },

    async fetchSlrPrb(token) {
      try {
        const data = await http.get("/noaa/curr-slr-prb", {
          headers: {
            Authorization: "Bearer " + token,
          },
        });

        this.currSlrPrb = data.data;
      } catch (error) {
        console.log(error);
      }
    },

    async fetchRecentAlert(token) {
      try {
        const data = await http.get("/noaa/recent-alert", {
          headers: {
            Authorization: "Bearer " + token,
          },
        });

        this.recentAlert = data.data;
      } catch (error) {
        console.log(error);
      }
    },
  },
});
