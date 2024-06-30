<template>
    <v-card variant="tonal" dense color="primary" class="kpi-card">
        <v-card-title style="color: #8D5B4C !important" class="text-center">Current KPI</v-card-title>
        <container fluid class="fill-height d-flex align-center justify-center">
            <v-row class="mb-2 mx-1">
                <v-col>
                    <v-card variant="tonal"
                        class="my-2 mx-2 d-flex flex-column align-center justify-center login-card">
                        <v-card-text class="">
                            <p style="color: white !important">{{ currKpi.kpis[1] }}</p>
                        </v-card-text>
                    </v-card>
                </v-col>
            </v-row>
        </container>
    </v-card>
</template>
<script setup>

import { defineProps, computed } from "vue";

const props = defineProps({
    data: {
        type: Object,
        required: true,
    },
});

const currKpi = computed(() => props.data);


</script>
<style scoped>
.kpi-card {
    margin-top: 30px;
}
.login-card {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}
</style>