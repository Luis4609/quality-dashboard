export const getColor = (data: number, previousData: number) => {
  const dataDiff = data - previousData;

  if (dataDiff === 0) {
    return 'text-warning mr-2 ml-2';
  }
  if (dataDiff > 0) {
    return 'text-success mr-2 ml-2';
  }
  return 'text-danger mr-2 ml-2';
};

export const getIcon = (data: number, previousData: number) => {
  const dataDiff = data - previousData;

  if (dataDiff === 0) {
    return 'equals';
  }
  if (dataDiff > 0) {
    return 'arrow-up';
  }
  return 'arrow-down';
};

export const getBackgroundColor = (data: number, previousData: number, successPercentage: number, dangerPercentage: number) => {
  const dataDiff = (data/previousData) * 100;

  if (dataDiff < dangerPercentage) {
    // ffc107 -f2a15e
    return { backgroundColor: '#AB2E3C' };
  }
  if (dataDiff > successPercentage) {
    // 6AB187 - 1BCFB4 - 198754 - 00d284
    return { backgroundColor: '#00d284' };
  }
  // AB2E3C
  return { backgroundColor: '#ffc107' };
};
