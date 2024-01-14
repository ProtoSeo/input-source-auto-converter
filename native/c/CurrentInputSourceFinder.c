//
//  CurrentInputSourceFinder.c
//  native
//
//  Created by 서승훈 on 1/14/24.
//

#include "CurrentInputSourceFinder.h"

int main(void) {
    findCurrentInputSource();
}

void findCurrentInputSource(void) {
    TISInputSourceRef inputSourceRef = TISCopyCurrentKeyboardInputSource();
        
    CFStringRef sourceId = TISGetInputSourceProperty(inputSourceRef, kTISPropertyInputSourceID);
    CFStringRef name = TISGetInputSourceProperty(inputSourceRef, kTISPropertyLocalizedName);
        
    int sourceIdLength = (int)CFStringGetLength(sourceId) * 4 + 1;
    char sourceIdStr[sourceIdLength];
    CFStringGetCString(sourceId, sourceIdStr, sourceIdLength, kCFStringEncodingUTF8);

    int nameLength = (int)CFStringGetLength(name) * 4 + 1;
    char nameStr[nameLength];
    CFStringGetCString(name, nameStr, nameLength, kCFStringEncodingUTF8);
            
    printf("%s (%s)", nameStr, sourceIdStr);
}
