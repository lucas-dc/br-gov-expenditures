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
  const [selectedOrgan, setSelectedOrgan] = useState('all');
  const [selectedBranch, setSelectedBranch] = useState('EXECUTIVE');
  const [allExpenses, setAllExpenses] = useState([]);
  const localeStyle = { style: 'currency', currency: 'BRL' };

  const year = 2025;
  const page = 1;

  useEffect(() => {
    axios.get(`/api/expenses/${year}?page=${page}`)
      .then(response => {
        setAllExpenses(response.data);
        setLoading(false);
        setChartData(generateChartData(response.data));
      })
      .catch(error => {
        console.error('Error fetching organ list:', error);
      });
  }, []);

  const handleOrganChange = (event) => {
    const organCode = event.target.value;
    setSelectedOrgan(organCode);

    if (organCode === 'all') {
      setChartData(generateChartData(allExpenses));
    } else {
      const organExpenses = allExpenses.filter(item => item.organCode === organCode);
      setChartData(generateChartData(organExpenses));
    }
  };

  const handleBranchChange = (e) => {
    setSelectedBranch(e.target.value);
  };

  const generateChartData = (expenses) => {
    const sortedExpenses = expenses.sort((a, b) => b.paid - a.paid);
    const label = sortedExpenses.map(item =>
      `${item.organName} - (${item.paid.toLocaleString('pt-BR', localeStyle)})`
    );
    const paid = sortedExpenses.map(item => item.paid);
    const backgroundColors = [
      generateRandomColor(), generateRandomColor(), generateRandomColor(), generateRandomColor(),
      generateRandomColor(), generateRandomColor(), generateRandomColor(), generateRandomColor(),
      generateRandomColor(), generateRandomColor(), generateRandomColor(), generateRandomColor(),
    ];

    return {
      labels: label,
      datasets: [
        {
          label: 'Total paid (R$)',
          data: paid,
          backgroundColor: backgroundColors,
          borderWidth: 1,
        }
      ]
    };
  };

  const getBranchData = (branch) => {
    const sortedExpenses = allExpenses.sort((a, b) => b.paid - a.paid);
    const branchExpenses = sortedExpenses.filter(item => item.branch === branch);
    const label = branchExpenses.map(item =>
      `${item.organName} - (${item.paid.toLocaleString('pt-BR', localeStyle)})`
    );
    const paid = branchExpenses.map(item => item.paid);
    const backgroundColors = [
      generateRandomColor(), generateRandomColor(), generateRandomColor(), generateRandomColor(),
      generateRandomColor(), generateRandomColor(), generateRandomColor(), generateRandomColor(),
      generateRandomColor(), generateRandomColor(), generateRandomColor(), generateRandomColor(),
    ];

    return {
      labels: label,
      datasets: [
        {
          label: `Total paid (R$)`,
          data: paid,
          backgroundColor: backgroundColors,
          borderWidth: 1,
        }
      ]
    };
  };

  const generateRandomColor = () => {
    const r = Math.floor(Math.random() * 256);
    const g = Math.floor(Math.random() * 256);
    const b = Math.floor(Math.random() * 256);
    return `rgb(${r},${g},${b})`;
  };

  if (loading) return <p>Loading chart...</p>;

  const allExpensesChartOptions = {
    plugins: {
      legend: {
        position: 'left',
        maxWidth: 500,
        labels: {
          usePointStyle: true
        },
      },
    },
  };

  const expensesByBranchChartOptions = {
    plugins: {
      legend: {
        position: 'right',
        maxWidth: 500,
        labels: {
          usePointStyle: true
        },
      },
    },
  };

  return (
    <div style={{ display: 'flex' }}>
      <div style={{ flex: 1 }}>
        <h3>Expenses by Organ</h3>

        {/* Dropdown for selecting an organ */}
        <select onChange={handleOrganChange} value={selectedOrgan}>
          <option key="all" value="all">All Organs</option>
          {allExpenses.map((expense) => (
            <option key={expense.organCode} value={expense.organCode}>
              {expense.organName}
            </option>
          ))}
        </select>

        {/* Pie chart for selected organ */}
        {selectedOrgan && chartData ? (
          <div>
            <Pie data={chartData} options={allExpensesChartOptions} />
          </div>
        ) : null}
      </div>

      {/* Pie charts for all branches */}
      <div style={{ flex: 1.7, paddingLeft: '50px' }}>
        <h3>Expenses Grouped by Branch</h3>
        <select onChange={handleBranchChange} value={selectedBranch}>
          {['EXECUTIVE', 'LEGISLATIVE', 'JUDICIARY'].map((branch) => (
            <option key={branch} value={branch}>
              {branch}
            </option>
          ))}
        </select>

        {/* Pie chart for selected branch */}
        {selectedBranch ? (
          <div style={{ maxHeight: '910px'}}>
            <Pie data={getBranchData(selectedBranch)} options={expensesByBranchChartOptions} />
          </div>
        ) : null}
      </div>

    </div>
  );
};

export default ExpensesPieChart;
