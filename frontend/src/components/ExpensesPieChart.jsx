import React, { useEffect, useState } from 'react';
import { Pie } from 'react-chartjs-2';
import axios from 'axios';
import {
  Chart as ChartJS,
  ArcElement,
  Tooltip,
  Legend,
} from 'chart.js';

ChartJS.register(ArcElement, Tooltip, Legend);

const ExpensesPieChart = () => {
    const [chartData, setChartData] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const orgCode = 65000;
    const year = 2025;
    const page = 1;

    axios.get(`/api/expenses/organ/${orgCode}/${year}?page=${page}`)
      .then(response => {
        const data = response.data;

        const labels = data.map(item => item.organ);
        const amounts = data.map(item => item.amount);

        const backgroundColors = [
          '#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0',
          '#9966FF', '#FF9F40', '#E7E9ED', '#7FB3D5'
        ];

        setChartData({
          labels: labels,
          datasets: [
            {
              label: 'Expenses by Organ',
              data: amounts,
              backgroundColor: backgroundColors,
              borderWidth: 1,
            }
          ]
        });

        setLoading(false);
      })
      .catch(error => {
        console.error('Error fetching data:', error);
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Loading chart...</p>;

  return (
    <div style={{ maxWidth: '500px', margin: 'auto' }}>
      <h3>Expenses by Organ</h3>
      <Pie data={chartData} />
    </div>
  );
};

export default ExpensesPieChart;