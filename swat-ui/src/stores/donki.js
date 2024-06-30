import { defineStore } from "pinia";
import http from "@/plugins/axios";

export const useDONKIStore = defineStore("donki", {
  state: () => ({
    isLoading: false,
    events: [],
    eventTypes: [],
    notifications: [],
    notificationTypes: [],
  }),
  getters: {
    getEvents(state) {
      return state.events;
    },
    getNotifications(state) {
      return state.notifications;
    },
  },
  actions: {
    async fetchNotificationData(token) {
      await this.fetchNotifications(token);
      await this.fetchNotificationTypes(token);
  
    },

    async fetchEventData(token) {
      await this.fetchEvents(token);
      await this.fetchEventTypes(token);
    },

    async fetchEvents(token) {
      try {
        const res = await http.get("/donki/events", {
          headers: {
            'Authorization': 'Bearer ' + token,
          }
        });

        this.events = res.data;
      } catch (error) {
        console.log(error);
        return [];
      }
    },

    async fetchEventTypes(token) {
      try {
        const res = await http.get("/donki/event-types", {
          headers: {
            'Authorization': 'Bearer ' + token,
          }
        });

        this.eventTypes = res.data;
      } catch (error) {
        console.log(error);
      }
    },

    async fetchNotifications(token) {
      try {
        const res = await http.get("/donki/notifications", {
          headers: {
            'Authorization': 'Bearer ' + token,
          }
        });

        this.notifications = res.data;
      } catch (error) {
        console.log(error);
      }
    },

    async fetchNotificationTypes(token) {
      try {
        const res = await http.get("/donki/notification-types", {
          headers: {
            'Authorization': 'Bearer ' + token,
          }
        });

        this.notificationTypes = res.data;
      } catch (error) {
        console.log(error);
      }
    },

    loading(isLoading) {
      this.isLoading = isLoading;
    },

  }
});
