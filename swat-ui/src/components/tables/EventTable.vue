<template>
  <v-row>
    <v-col cols="3">
      <v-select v-model="eventType" :items="eventTypes" label="Select Event Type" item-value="eventType"
        item-text="eventType" color="secondary" return-object>
      </v-select>
    </v-col>
    <v-col cols="3">
      <v-text-field v-model="search" label="Search" prepend-inner-icon="mdi-magnify" variant="outlined" hide-details
        single-line color="secondary"></v-text-field>
    </v-col>
    <v-spacer />
    <v-col cols="1" class="d-flex justify-end">
      <v-icon size="large" color="secondary" class="export-icon" @click="exportJson">mdi-file-export-outline</v-icon>
    </v-col>
  </v-row>

  <v-data-table :headers="headers" :items="items" :search="search" :loading="isLoading" class="elevation-5"
    height="70vh" fixed-header>
  </v-data-table>
</template>

<script setup>
import { ref, watch, onMounted, computed } from "vue";
import { useAppStore } from "@/stores/app";
import { useDONKIStore } from "@/stores/donki";
import { storeToRefs } from "pinia";

// Define Stores
const donki = useDONKIStore();
const app = useAppStore();

const { isLoading, events, eventTypes } = storeToRefs(donki);

// Select Props
const eventType = ref("");

// Table Props
const headers = ref([]);
const items = ref([]);
const search = ref("");

// Object Headers
const cmeHeaders = ref([]);
const flrHeaders = ref([]);

// Watch for event type change, set headers, show data.
watch(eventType, () => {

  if (eventType.value === "CME") {
    headers.value = cmeHeaders.value;
  } else if (eventType.value === "FLR") {
    headers.value = flrHeaders.value;
  }

  items.value = events.value.filter((item) => item.eventType === eventType.value);
});

// Fetch Table data from store, set defaults
async function setDefaults() {
  donki.loading(true);

  const user = computed(() => app.user);
  const token = user.value.token;

  if (token) {

    await donki.fetchEventData(token);

  }
  else {
    console.log('Unable to authenticate user.')
  }

  eventType.value = eventTypes.value.at(0);

  // Set header data
  cmeHeaders.value = [
    { key: "activityID", title: "Activity ID", width: "15%" },
    { key: "eventType", title: "Event Type", width: "10%" },
    { key: "startTime", title: "Start Time", width: "10%" },
    { key: "note", title: "Note", width: "55%" },
    { key: "catalog", title: "Catalog", width: "10%" },
  ];

  flrHeaders.value = [
    { key: "flrID", title: "Flare ID", width: "16%" },
    { key: "classType", title: "Class Type", width: "16%" },
    { key: "sourceLocation", title: "Active Region #", width: "16%" },
    { key: "beginTime", title: "Begin Time", width: "16%" },
    { key: "peakTime", title: "Peak Time", width: "16%" },
    { key: "endTime", title: "End Time", width: "16%" }
  ];

  if (eventType.value === "CME") {
    headers.value = cmeHeaders.value;
  } else if (eventType.value === "FLR") {
    headers.value = flrHeaders.value;
  }

  items.value = events.value.filter((item) => item.eventType === eventType.value);
  eventTypes.value = eventTypes.value.sort();

  donki.loading(false);
}

const exportJson = () => {
  const data = {
    data: events.value.filter((item) => item.eventType === eventType.value) 
  };

  const json = JSON.stringify(data, null, 2);
  const blob = new Blob([json], { type: "application/json" });
  const url = URL.createObjectURL(blob);
  const link = document.createElement("a");
  link.href = url;
  link.download = "data.json";
  link.click();
  URL.revokeObjectURL(url);
};

onMounted(() => {
  setDefaults();
});
</script>

<style scoped>
.export-icon {
  margin-top: 32px;
}
</style>