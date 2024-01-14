 // Get year, month, and day part from the date
 const date = new Date();
 const year = date.toLocaleString('default', { year: 'numeric' });
 const month = date.toLocaleString('default', { month: '2-digit' });
 const day = date.toLocaleString('default', { day: '2-digit' });

 // Generate yyyy-mm-dd date string
export const formattedCurrentDate = year + '-' + month + '-' + day;
export const formattedStartDate = year + '-' + month + '-' + '01';


