# Ada-Web-Highcharts [![version](https://img.shields.io/badge/version-0.9.0-green.svg)](https://ada-discovery.github.io) [![License: CC BY-NC 3.0](https://img.shields.io/badge/License-CC%20BY--NC%203.0-lightgrey.svg)](https://creativecommons.org/licenses/by-nc/3.0/)

This is an extension of Ada Web with a widget engine backed by [Highcharts JS library](https://www.highcharts.com). Note that the Plotly-based widget engine, provided by default, has not been replaced, rather can be used alongside Highcharts wherever needed.

#### Configuration

The default widget engine is set to `Highcharts` in the configuration as

```
widget_engine.defaultClassName = "HighchartsWidgetEngine"
```

If you wish to switch it to Plotly (as provided by a regular ada-web) add the following line to `custom.conf`

```
widget_engine.defaultClassName = "PlotlyWidgetEngine"
```

Furthermore, you can explicitly choose a widget engine (`Plotly` or `Highcharts`) for each data set independently by selecting `Widget Engine Name` under `Settings` -> `General`. 

#### License

The project and all its source code is distributed under the terms of the <a href="https://creativecommons.org/licenses/by-nc/3.0/">CC BY-NC 3.0 license</a> - that means free to use for _non-commercial purposes only_. This restriction is enforced solely by Highcharts library (not Ada itself).
Alternatively, if you do want to use this project for commercial activities you can do so by obtaining a [valid license from Highcharts](https://shop.highsoft.com/developer-license).
