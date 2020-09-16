package com.kriswantoro.indramayu.util

object Constant {

    const val API_BASE_PATH = "http://192.168.43.1/smartcityindramayu/"
//    const val API_BASE_PATH = "http://10.0.2.2/smartcityindramayu/"

    const val IMAGE_EMPTY = "data:image/jpeg;base64,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A8U8A408888U488s88c88M8scUsMc488008s88s888cc888s48c0ss8s88ss88c8888880s8888M8o8oMcccsM8M8Es884csE8c8oss8888E848s48088o848Qg8848sU8888co88c8E8s8M4cs8888Qs4488o88E8c8Ms888c8A8s8Msc884sYkY0U88s8sE8c8Ms88888U8884880I4M0M8Isc8s88c888888M8c8gso8QMUMQocw888Mg4g84QcUs8c88888U848wM0cYAAAAAAAAQI88w808w888888E8s8IoY8A4MY88Q88oE80E8c80888s88U88kscg0AoIAs4QI0oU8cU888c888sscEc48AI4UAs88sEAwookMsQ80sAMs8888E8s8s4k4A8oEMgAAgoA88M8c8c8s8888E8s448MQA4QAAAAAUgQ80c8c88888888A8o8s8sgAgAgAAAAAAQ8sE8c8c88wMccEss8IMsUAAAAAAAAAAEs0E8cscUM8888U8s8Yk8E88g8I8Uo88s88Y8Msk8o8w4UE4o8Qgw00wg4QwQggwkQsU0QowUAw888c8s8888s488888cs88s88c8888888AAUkMIQQEgEMIAEIEAIAAAE0EMMogQAM888U8s4os8U88o8M8cs48s80M888M8o8ww0UoY8ww4U04g0wwQowwgw8c004w0g8M8cU88oI88ccss8scMsMMccUU88sc8s8/8QAFBEBAAAAAAAAAAAAAAAAAAAAkP/aAAgBAwEBPxBIP//EABQRAQAAAAAAAAAAAAAAAAAAAJD/2gAIAQIBAT8QSD//xAArEAAABAQGAQMFAQAAAAAAAAAAEDFxAREg8CEwQEFRYVCBkaFgscHh8dH/2gAIAQEAAT8QqR4ojQuJ1bqTqHE43Bwcbg4OJwcHBwcHVOzDjcHUQ8kABGaQoI8KAIoXSEaECPoIwAA7xIdoXaAdS4nUOqcTqXaF2jdS7wjstAR4gAABARQR9IABARqwRmiAjSiM4lXlRlTDsHgEsuRmyow8CABchMPzsDcB/AgaCgyJHQBGhCNQ9DAB/BAggOMewoYY0F3MxBizCRGhhSEaUAvUGAnY5U3B0jGgXwKmCJQOjPlP3EegzDVgUMrESOULBFBGhCAjRg8U3YG7HMh/lngUILohgoYsBwc/MR4cAIUnRPrtsUNhLt5go5aPDBxA++AaYkhG2E9YiA/0BSiSoFvx7ENYoDGNIgB7mMCoTBiCHER9wIgfAnA1BJWjPC5Fw40aMQYP4AQx4FGOmRisp0goKRJaODIiTSZE86tlLCYTDYTB7Cg/wT0AFQkekjkGJllQIAtIklAbCUfissOIiIiIjsBRUgkYlwGAAUhER4A5FSYTCYbDZloCMg/YCJIi2UCcRzRr8kcQcCKEBPNFEmm+woL14J0f2jCBbxEyRA8sgBHkgPcBBOhYuDiMMu4iziRrOIu4ka7iLuIu4iziLuJGu4i7iLOIu4ixiFUxFiFcd+DH3AIJD0JYKIgdoix0GJ8I5OxcaQE//wD/AP8A/wD/APz/AP7B0y+R93hAAAkiCmGxe2EEqdi4qABSKMAPj5TjOBauRvFx3qgjKfExESJJNbDsIGexcUAaiTBYj9wJJEpICzYt1QCAwiqHFVq5G8XXdJGlARlA+AGFiPcIBCUAQ9RHwIZBJYuM0AIAVMP6Agi5YQd1IBauRvF13qgAEZbCDhci4liA/gJEMFi4oA+JMKKR9AolBwLs549keonRKHR0AE84PxVi/wBpwmA+QckPkQLHauSuu6UaUCAjIBBPkSQiAQjihsMQKTsMSIsXFIAnxEbA44H+OOtB/AKghSf1XjblRUkAJCVhgoDHR4oCHrVyVx3QI8EAAYEQShIPcRAFFi4ywS87ZpahFRiZsJMlj7GStXJXXerARlm4ikqPiIncXAWLioEh/NHhI+3HpFItxPRgJn3CibelWSDQdhuJg7MCtXI3i470QSyKMwwCBCwEbOfriNoXLsRA5oRaoGOH+7CI/YHABGGfsIC2VGbDlJYnKNG+j4A1AkVq5K478OHvAEqF7TVclSDYDCH3Oh/ANI6yN9CgSnLhGJWrkbxdd6oRmkMm0OqgXYhowfwYsQ6qaTRvpg8AmBQGRkLVyQuO9UQEZhM6Q6BsnBxYMMaQfoS0GeIQBCEoShKEq3YFigWrkrruhAR4MBJvH5QhpZMTAzMzMzMzMzMz7otTCuO9GkDg4SE4kBBOpHEcZIO0pBCjAioOIm8nh5dA6A8dBPJ5vDzuOycaKDjcZxODshGeBEW8SKCmmu7u7u7u7u7u7u76aUrCkR9PAAABlDKmBlDKjNQMyCAjKEa4AgI0oAI8wAAAAAAAAk+lwDPqcAAAAAAAAGIyHEZH0UyFFAkdiMqchkMhxPKruQ4jIZDifZUSMoMipDLTQdW6g6o7UAdkIzjGvjmcdNY5c4SeVAJAwdgYJCQchJDkIwSF2CQpDMDAwdhSBgkEgQJCYOwSCQSBg7AwSUyEkOQkgkKQMMcaQkZIIpJyyHIU9FxiBIUlIA4klBGlABHigjP/AP/Z"
}
