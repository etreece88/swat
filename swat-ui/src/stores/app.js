import { defineStore } from "pinia";
import http from "@/plugins/axios";

export const useAppStore = defineStore("app", {
  state: () => ({
    user: JSON.parse(sessionStorage.getItem("user")) || null,
    snackbar: {
      show: false,
      message: "",
      color: "",
      timeout: 3000,
    },
    isLoading: false,
    roles: []
  }),
  getters: {
    isAuthenticated: (state) => state.user != null ? !!state.user.token : false,
    isAdmin: (state) => state.roles.includes('ROLE_ADMIN') ? true : false
  },

  actions: {
    async login(credentials) {
      try {
        const res = await http.post("/user/login", credentials);

        this.setUser(res.data);

        return true;
      } catch (error) {
        console.log(error);
        return false;
      }
    },

    async signup(credentials) {
      try {
        const res = await http.post("/user/signup", credentials);

        this.setUser(res.data);

        return true;
      } catch (error) {
        console.log(error);
        return false;
      }
    },

    async getUsers(token) {
      try {
        const res = await http.get("/user/users", {
          headers: {
            Authorization: "Bearer " + token,
          },
        });

        return res.data; 

      } catch (error) {
        console.log(error);
      }
    },

    async getRoles(token) {
      try {
        const res = await http.get("/user/roles", {
          headers: {
            Authorization: "Bearer " + token,
          },
        });

        return res.data; 

      } catch (error) {
        console.log(error);
      }
    },


    async deleteUser(token, username) {
      try {
        const res = await http.post("/user/delete-user", null, {
          params: {
            username: username
          },
          headers: {
            Authorization: "Bearer " + token,
          },
        });

        return res.status; 

      } catch (error) {
        console.log(error);
      }
    },

    async changeRole(token, username, role) {
      try {
        const res = await http.post("/user/change-role", null, {
          params: {
            username: username,
            role: role
          },
          headers: {
            Authorization: "Bearer " + token,
          },
        });

        return res.status; 

      } catch (error) {
        console.log(error);
      }
    },

    setUser(data) {
      sessionStorage.setItem("user", JSON.stringify(data));
      this.user = data;
    },

    expireUser() {
      this.user = null;
      this.roles = null;
      sessionStorage.removeItem("user");
    },

    loadUserFromSession() {
      const userData = sessionStorage.getItem("user");
      if (userData) {
        this.roles = [];
        this.user = JSON.parse(userData);
        this.roles.push(this.user.roles);
      }
    },

    showSnackbar(message, color) {
      this.snackbar.show = true;
      this.snackbar.message = message;
      this.snackbar.color = color;
    },

    hideSnackbar() {
      this.snackbar.show = false;
      this.snackbar.message = "";
    },

    loading(isLoading) {
      this.isLoading = isLoading;
    },

  },
});
