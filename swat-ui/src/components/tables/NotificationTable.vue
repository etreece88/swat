<template>
  <v-row>
    <v-col cols="3">
      <v-select v-model="notificationType" :items="notificationTypes" label="Select Notification Type"
        item-value="notificationType" item-text="notificationType" color="secondary" return-object>
      </v-select>
    </v-col>
    <v-col cols="3">
      <v-text-field v-model="search" label="Search" prepend-inner-icon="mdi-magnify" variant="outlined" hide-details
        color="secondary" single-line></v-text-field>
    </v-col>
  </v-row>

  <v-data-table v-model="expanded" :headers="headers" :items="items" :search="search" :loading="isLoading"
    :color="secondary" class="elevation-5" height="70vh" fixed-header show-expand>
    <template v-slot:expanded-row="{ columns, item }">
      <tr>
        <td :colspan="columns.length">
          {{ item.messageBody }}
        </td>
      </tr>
    </template>
  </v-data-table>
</template>

<script setup>
import { ref, watch, onMounted, computed } from "vue";
import { useDONKIStore } from "@/stores/donki";
import { useAppStore } from "@/stores/app";
import { storeToRefs } from "pinia";

// Define Stores
const donki = useDONKIStore();
const app = useAppStore();

const { isLoading, notifications, notificationTypes } = storeToRefs(donki);


const notificationType = ref("");

// Table Props
const headers = ref([]);
const items = ref([]);
const search = ref("");
const loading = ref(Boolean);
const expanded = ref([]);

// Watch for notification type change, filter data.
watch(notificationType, () => {
  loading.value = true;
  if (notificationType.value === "ALL" || notificationType.value === "") {
    items.value = notifications.value;
  } else {
    items.value = notifications.value.filter((item) => item.messageType === notificationType.value);
  }

  loading.value = false;
});

async function setDefaults() {
  // Get data from store
  donki.loading(true);

  const user = computed(() => app.user);
  const token = user.value.token;

  if (token) {

    await donki.fetchNotificationData(token);

  }
  else {
    console.log('Unable to authenticate user.')
  }
  
  items.value = notifications.value;
  
  notificationType.value = "ALL";
  notificationTypes.value = notificationTypes.value.sort();

  // Set header data
  headers.value = [
    { key: "messageID", title: "Notification ID", width: "25%" },
    { key: "messageType", title: "Notification Type", width: "25%" },
    { key: "messageIssueTime", title: "Notification Time", width: "25%" },
    { key: "messageURL", title: "URL", width: "25%" },
    { key: "data-table-expand", title: "" },
  ];

  donki.loading(false);
}

onMounted(() => {
  setDefaults();
});
</script>

<style scoped>
.v-icon {
  color: #fcab10 !important;
}
</style>