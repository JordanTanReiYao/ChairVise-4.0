export const deepCopy = (obj) => {
  return JSON.parse(JSON.stringify(obj));
};

//    deepcopy(predefinedMapping.dbTagIndices), dbSchema.fieldMetaDataList
// or deepcopy(predefinedMapping.importedTagIndices), data.uploadedLabel
export const filterPredefinedMap = (mappedIdArray, originalArray) => {
  let result = [];
  for (let i = 0; i < mappedIdArray.length; i++) {
    if (mappedIdArray[i] < originalArray.length) {
      result.push(mappedIdArray[i]);
    }
  }
  return result;
};

const convert_string = require("convert-string");
export const anonymizeName = (original) => {
  var bytes = convert_string.stringToBytes(original);
  var anon = "";
  for(var a=0;a<bytes.length;a++){
      anon = anon.concat(String(bytes[a]+18));
  }
  return anon;
};