/* eslint-disable @typescript-eslint/require-await */
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';

// PDF
export const generatePdf = async ( pdfBody: { current: string | HTMLElement; }, pdfName: string ) => {
  const pdf = new jsPDF('portrait', 'pt', 'a4');
  // Adding the fonts
  pdf.setFont('Inter-Regular');
  pdf.setFontSize(8);

  // data
  const data = await html2canvas(document.querySelector('#pdfChats'));
  const img = data.toDataURL('image/png');
  const imgProperties = pdf.getImageProperties(img);
  const pdfWidth = pdf.internal.pageSize.getWidth();
  const pdfHeight = (imgProperties.height * pdfWidth) / imgProperties.width;

  pdf.addImage(img, 'PNG', 0, 85, pdfWidth, pdfHeight);

  pdf.html(pdfBody.current, {
    // eslint-disable-next-line @typescript-eslint/no-shadow, @typescript-eslint/no-misused-promises
    async callback(doc) {
      doc.save(pdfName);
    },
  });
};
