<template>
  <div>
    <div id="mapResults" class="map">
    </div>
  </div>
</template>

<script>
import 'ol/ol.css';
import { Circle, Fill, Style, Text } from 'ol/style';
import { Feature, Map, Overlay, View } from 'ol/index';
import { OSM, Vector as VectorSource } from 'ol/source';
import { Point } from 'ol/geom';
import { Vector as VectorLayer } from 'ol/layer';
import { useGeographic } from 'ol/proj';
import TileLayer from 'ol/layer/Tile';
export default {
    name: "OLMap",
    data() {
        return {
            place: [20, 45],
            mapResults: null,
            element: null,
        }
    },
    methods: {
        initResultsMap: function(popupEnabled, latLongMapCenter, func) {
            this.mapResults = new Map({
                target: 'mapResults',
                view: new View({
                center: [latLongMapCenter[1], latLongMapCenter[0]],
                zoom: 9,
                }),
                layers: [
                new TileLayer({
                    source: new OSM(),
                }),
                ],
            });
        },

        addAllApartmentPointsResultMap: function(orderedLatLongList, showNumber) {
            for (var i in orderedLatLongList)
                this.mapResults.addLayer(new VectorLayer({
                source: new VectorSource({
                    features: [new Feature(new Point([orderedLatLongList[i][1], orderedLatLongList[i][0]]))],
                }),
                style: new Style({
                    image: new Circle({
                    radius: 10,
                    fill: new Fill({ color: 'red' }),
                    }),
                    text: new Text({
                    font: "bold 12px Arial, Verdana, Helvetica, sans-serif",
                    text: showNumber ? (Number(i) + 1).toString() : "",
                    }),
                }),
                }));
        },

        removeAllApartmentPointsResultMap: function() {
            this.mapResults.getLayers().forEach(function(layer) {
                if (layer instanceof VectorLayer) {
                this.mapResults.removeLayer(layer);
                }
            });
        }
    },
    created(){
    },
    mounted() {
        useGeographic();
        this.initResultsMap(true, [45, 20], undefined);
        this.addAllApartmentPointsResultMap([
            [0, 0],
            [45, 20]
        ], false);
    },
}
</script>

<style>
        .map {
            width: 60%;
            height: 500px;
        }
        
        td {
            padding: 0 0.5em;
            text-align: right;
        }
</style>