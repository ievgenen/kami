import commons from './data/commonAspects';
import doingBusns from './data/doingBusnsAspects';
import sciTech from './data/scienceTechAspects';
import educations from './data/educationAspects';


export default {
  getCommonAspects: ((callback) => callback(commons)),
  getDoingBusinessAspects: ((callback) => callback(doingBusns)),
  getScienceTechAspects: ((callback) => callback(sciTech)),
  getEducationalAspects: ((callback) => callback(educations)),
}