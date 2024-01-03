// Opciones para las gráficas de métricas de llamadas
// Charts from ECharts

export const atendidasSVDIOption = {
  title: {},
  tooltip: {},
  legend: { top: 'bottom' },
  xAxis: [
    {
      name: 'Día',
      data: ['1', '4', '5', '6', '7', '8', '11', '12', '13', '15', '18', '19', '20', '21', '22', '25', '26', '27', '28'],
    },
  ],
  yAxis: [
    {
      type: 'value',
      name: 'Nº de Chats',
      min: 200,
      max: 3200,
      interval: 300,
      axisLabel: {
        formatter: '{value}',
      },
    },
  ],
  series: [
    {
      name: 'Chats recibidos',
      type: 'line',
      data: [353, 482, 619, 3037, 2569, 1261, 2260, 1312, 890, 729, 1061, 1762, 861, 563, 702, 730, 623, 554, 359],
    },
    {
      name: 'Chats atendidos',
      type: 'line',
      data: [304, 380, 421, 696, 624, 648, 756, 649, 621, 483, 566, 554, 452, 392, 444, 497, 434, 418, 321],
    },
  ],
};

export const recibidasAtendidasOption = {
  title: {},
  tooltip: {},
  legend: { top: 'bottom' },
  xAxis: {
    data: ['Ene', 'Feb', 'Mar', 'Abr'],
  },
  yAxis: [
    {
      type: 'value',
      name: 'Recibidas',
      min: 0,
      max: 50000,
      interval: 10000,
      axisLabel: {
        formatter: '{value}',
      },
    },
    {
      type: 'value',
      name: 'Atendidas',
      min: 0,
      max: 100,
      interval: 20,
      axisLabel: {
        formatter: '{value} %',
      },
    },
  ],
  series: [
    {
      name: 'Llamadas recibidas',
      type: 'bar',
      label: {
        show: true,
        position: 'inside',
      },
      data: [38681, 36357, 35012, 38555],
    },
    {
      name: '% Llamadas atendidas',
      type: 'line',
      yAxisIndex: 1,
      label: {
        show: true,
        position: 'up',
      },
      data: [84.98, 78.91, 86.84, 86.47],
    },
  ],
};
