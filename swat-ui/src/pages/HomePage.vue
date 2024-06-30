<template>
  <v-container v-if="!isLoading" fluid>
    <v-row>
      <v-col>
        <current-condition :data="currCon" class="text-center" />
      </v-col>
    </v-row>


    <v-row>
      <v-col cols="4">
        <current-KPI :data="currKpi" class="text-center" />
      </v-col>

      <v-spacer />

      <v-col cols="4" class="d-flex justify-center">
        <div class="image-container">
          <img
            class="sun-image"
            :src="currentImage"
            alt="Sun Today"
            width="400px"
            height="400px"
          />
        </div>
      </v-col>

      <v-spacer />

      <v-col cols="4" >
        <flare-prob :data="currSlrPrb" class="text-center" />
      </v-col>
    </v-row>

    <v-row class="mt-5">
      <v-col>
        <current-winds :data="currSlrWnd" class="text-center" />
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <RecentAlert :data="recentAlert" class="text-center" />
      </v-col>
    </v-row>
  
  </v-container>
  <v-app v-else>
    <div>
      <div class="loading-overlay">
        <v-app-bar app class="flex text-center">
          <v-toolbar-title class="justify-center align-center">
            <v-avatar image="@/assets/swat3.png" class="logo mr-1"></v-avatar>
            Space Weather Alerts and Tracking
          </v-toolbar-title>
        </v-app-bar>
        <v-progress-circular indeterminate color="icon" size="64"></v-progress-circular>
      </div>
    </div>
  </v-app>
</template>

<script setup>
import { onBeforeMount, onMounted, computed, shallowRef, ref } from "vue";
import { useAppStore } from "@/stores/app";
import { useNOAAStore } from "@/stores/noaa";
import { storeToRefs } from "pinia";

const app = useAppStore();
const noaa = useNOAAStore();
const { isLoading } = storeToRefs(app);

const currCon = shallowRef({})
const currKpi = shallowRef({})
const currSlrWnd = shallowRef({})
const currSlrPrb = shallowRef({})
const recentAlert = shallowRef({})


const imgUrls = [
  "https://services.swpc.noaa.gov/images/animations/suvi/primary/171/latest.png",
  "https://services.swpc.noaa.gov/images/animations/suvi/primary/304/latest.png",
  "https://services.swpc.noaa.gov/images/animations/suvi/primary/284/latest.png",
  "https://services.swpc.noaa.gov/images/animations/sdo-hmii/latest.jpg",
]
const currentIndex = ref(0);
const currentImage = ref(imgUrls[currentIndex.value]);

const init = async () => {
  app.loading(true);
  const user = computed(() => app.user);
  const token = user.value.token;

  if (token) {
    await noaa.fetchData(token);

    currCon.value = noaa.currCon;
    currKpi.value = noaa.currKpi;
    currSlrWnd.value = noaa.currSlrWnd;
    currSlrPrb.value = noaa.currSlrPrb;
    recentAlert.value = noaa.recentAlert;


  } else {
    console.log("Unable to authenticate user.");
  }

  app.loading(false);
};


onBeforeMount(() => {
  init();
});

const updateImage = () => {
  currentIndex.value = (currentIndex.value + 1) % imgUrls.length;
  currentImage.value = imgUrls[currentIndex.value];
};

onMounted(() => {
  setInterval(updateImage, 10000); // Change 100 to the desired frame rate (milliseconds)
});
</script>
<style scoped>
.image-container {
  width: 200px; /* Adjust as needed for medium size */
  height: 200px; /* Adjust as needed for medium size */
  border-radius: 50%;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}

.sun-image {
  width: 300px; /* Scale up to zoom in */
  height: 300px; /* Scale up to zoom in */
  object-fit: cover; /* Ensures the image is cropped properly */
  position: absolute;
  top: -50px; /* Adjust positioning to center the zoomed image */
  left: -50px; /* Adjust positioning to center the zoomed image */
}
</style>